package com.booklet.bookletandroid.presentation.fragments.newschedulecard

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.booklet.bookletandroid.R
import com.booklet.bookletandroid.databinding.FragmentCardBinding
import com.booklet.bookletandroid.domain.DateHelper
import com.booklet.bookletandroid.domain.Utils
import com.booklet.bookletandroid.domain.model.Date.Companion.toDate
import com.booklet.bookletandroid.domain.model.Time
import com.booklet.bookletandroid.domain.px
import com.booklet.bookletandroid.presentation.fragments.schedulecard.ScheduleCardFragment
import com.booklet.bookletandroid.presentation.model.schedule.Day
import com.booklet.bookletandroid.presentation.model.schedule.Homework
import com.booklet.bookletandroid.presentation.model.schedule.Mark
import kotlinx.android.synthetic.main.fragment_card.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.*

class NewScheduleCardFragment : Fragment() {
    private lateinit var mViewModel: NewScheduleCardViewModel
    private lateinit var mBinding: FragmentCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mViewModel = ViewModelProvider(this).get(NewScheduleCardViewModel::class.java)
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_card,
                container,
                false)
        mBinding.viewModel = mViewModel

        mViewModel.mDate =
                requireArguments().getString(ScheduleCardFragment.ARGUMENT_DATE)!!.toDate()

        val view = mBinding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val params = cardList.layoutParams as LinearLayout.LayoutParams
        params.setMargins(0, 118.px, 0, 0)
        cardList.layoutParams = params
        Log.d(TAG, "CardList инициализирован в карточке ${mViewModel.mDate}.")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        EventBus.getDefault().register(this)
        Log.d(TAG, "EventBus инициализирован в карточке ${requireArguments()
                .getString(ScheduleCardFragment.ARGUMENT_DATE)!!}.")
    }

    override fun onDetach() {
        EventBus.getDefault().unregister(this)
        Log.d(TAG, "EventBus деинициализирован в карточке ${mViewModel.mDate}.")

        super.onDetach()
    }

    @Subscribe
    fun onReceivedDataEvent(days: List<Day>) {
        Log.d(TAG, "Данные получены карточкой ${mViewModel.mDate}.")

        // data.days!!.filter { it!!.date == requireArguments().getString(ARGUMENT_DATE) }[0]

        paintSchedule(days[0])
    }

    private fun setEmptyContentLayout() {
        cardHeader.text = "Уроков нет"
    }

    /**
     * Метод отвечает за отрисовку отдельной карточки в расписании.
     *
     * На вход подаётся объект Day. Далее из него извлекаются данные и
     * записываются в объект addableView типа LinearLayout.
     * addableView представляет собой вертикальный список из предметов,
     * прим.: 1. Математика; 2. Русский язык; ...
     *
     * Затем сформированный объект addableView добавляется текущему фрагменту
     * с помощью метода setContentLayout(addableView).
     *
     * @param day это объект-день, который требуется отрисовать на карточке.
     */
    private fun paintSchedule(day: Day) {
        doAsync {
            if (day.subjects.isEmpty())
                uiThread { setEmptyContentLayout() }

            val height = 12.px

            val subjectsView = LinearLayout(context)
            subjectsView.orientation = LinearLayout.VERTICAL

            var previous = day.subjects[0]
            val layoutInflater = LayoutInflater.from(context)
            val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

            for (subject in day.subjects) {
                layoutParams.setMargins(0, 0, 0, height)

                val subjectView = layoutInflater
                        .inflate(R.layout.item_schedule, null, false)
                subjectView.findViewById<TextView>(R.id.scheduleItemIndex)
                        .text = subject.number.toString()
                subjectView.findViewById<TextView>(R.id.scheduleItemSubject).text = subject.name

                val homeworkDescription = subjectView.addHomeworks(subject.homeworks)
                subjectView.prepareLinkView(requireContext(), homeworkDescription)
                subjectView.addMarks(requireContext(), subject.marks)
                prepareTimeView(subjectView, subject.number, subject.beginTime, subject.endTime,
                        previous.endTime)

                subjectView.setOnLongClickListener {
                    Utils.copyToClipboard(requireContext(), "Д/З", homeworkDescription)
                    requireContext().toast("Д/З скопировано")
                    true
                }

                previous = subject
                subjectsView.addView(subjectView, layoutParams)
            }

            uiThread { setContentLayout(subjectsView) }
        }
    }

    private fun View.addMarks(context: Context, marks: List<Mark>) {
        val marksView: LinearLayout = this.findViewById(R.id.scheduleMarks)

        var isCoefficients = false
        for ((index, mark) in marks.withIndex()) {
            val view = LayoutInflater.from(context)
                    .inflate(R.layout.layout_mark, null,
                            false) as RelativeLayout
            val markView: TextView = view.find(R.id.scheduleItemMark)
            val weightView: TextView = view.find(R.id.scheduleItemMarkWeight)
            val param = RelativeLayout.LayoutParams(22.px, 22.px)
            if (index < marks.size) {
                param.marginEnd = 4.px
            }
            markView.layoutParams = param

            when (mark.markType) {
                Mark.MarkType.GRADE -> {
                    markView.text = mark.score.toString()
                    markView.background = when (mark.score) {
                        "5" -> ContextCompat.getDrawable(context,
                                R.drawable.background_mark_five)
                        "4" -> ContextCompat.getDrawable(context,
                                R.drawable.background_mark_four)
                        "3" -> ContextCompat.getDrawable(context,
                                R.drawable.background_mark_three)
                        else -> ContextCompat.getDrawable(context,
                                R.drawable.background_mark_two)
                    }

                    if (mark.weight != null && mark.weight > 1) {
                        weightView.text = mark.weight.toString()
                        param.marginEnd = (-6).px
                        isCoefficients = true
                    } else {
                        weightView.visibility = View.GONE
                    }
                }

                else -> {
                    markView.background = ContextCompat
                            .getDrawable(context, R.drawable.background_point)
                    val params = RelativeLayout.LayoutParams(8.px, 8.px)
                    params.marginEnd = 24.px
                    markView.layoutParams = params
                }
            }

            marksView.addView(view)
        }

        if (marks.isEmpty())
            marksView.visibility = View.GONE
        if (isCoefficients)
            marksView.topPadding = 8.px
    }

    private fun View.addHomeworks(assignments: List<Homework>): String {
        val homeworkView = this.findViewById<TextView>(R.id.scheduleItemHomework)

        // TODO: Вынести блок кода получения текста Д/З.
        val homeworkDescription = assignments[0].text ?: ""

        if (homeworkDescription.isEmpty())
            homeworkView.visibility = View.GONE
        else
            homeworkView.text = homeworkDescription

        return homeworkDescription
    }

    private fun View.prepareLinkView(context: Context, homeworkDescription: String) {
        val linkView = this.findViewById<ImageView>(R.id.scheduleItemLink)
        val urls = Utils.extractUrls(homeworkDescription)
        if (urls.isNotEmpty()) {
            linkView.setOnClickListener {
                context.browse(urls[0])
            }
            linkView.visibility = View.VISIBLE
        }
    }

    private fun prepareTimeView(local: View,
                                lessonNumber: Int,
                                beginTime: Time,
                                endTime: Time,
                                previousEndTime: Time) {
        val timeView = local.findViewById<TextView>(R.id.scheduleItemTime)

        val timeText = "${beginTime.hour.toString().padStart(2, '0')}:" +
                "${beginTime.minute.toString().padStart(2, '0')}-" +
                "${endTime.hour.toString().padStart(2, '0')}:" +
                "${endTime.minute.toString().padStart(2, '0')}"
        val currentDate = DateHelper.getDate().format("DD.MM.YYYY")

        if (currentDate == mViewModel.mDate.toString()
                && DateHelper.isTimeInInterval(DateHelper.getCurrentTime(), beginTime, endTime)) {
            timeView.background = ContextCompat
                    .getDrawable(requireContext(), R.drawable.background_current_lesson)
            timeView.textColor = Color.WHITE
            val paddingDp = 6.px
            timeView.setPadding(paddingDp, 0, paddingDp, 0)
            timeView.text = "$timeText — ${requireContext().getString(R.string.currentLesson)}"
        } else if (currentDate == mViewModel.mDate.toString() && lessonNumber > 1 &&
                DateHelper.isTimeInInterval(DateHelper.getCurrentTime(),
                        previousEndTime,
                        beginTime)) {
            timeView.background = ContextCompat
                    .getDrawable(requireContext(), R.drawable.background_next_lesson)
            timeView.textColor = Color.WHITE
            val paddingDp = 6.px
            timeView.setPadding(paddingDp, 0, paddingDp, 0)
            timeView.text = "$timeText — ${requireContext().getString(R.string.next_lesson)}"
        } else {
            timeView.text = timeText
        }
    }

    private fun setContentLayout(layout: ViewGroup) {
        cardList.removeAllViews()

        layout.alpha = 0f
        cardList.addView(layout)

        layout.animate()
                .alpha(1F)
                .duration = 100
    }

    companion object {
        private val TAG = this::class.java.simpleName

        const val ARGUMENT_POSITION = "arg_page_position"
        const val ARGUMENT_DATE = "arg_date"

        fun newInstance(position: Int, date: String): NewScheduleCardFragment {
            val fragment = NewScheduleCardFragment()
            val arguments = Bundle()
            arguments.putInt(ARGUMENT_POSITION, position)
            arguments.putString(ARGUMENT_DATE, date)
            fragment.arguments = arguments
            return fragment
        }
    }
}