package com.booklet.bookletandroid.presentation.fragments.newschedulecard

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
import com.booklet.bookletandroid.data.model.booklet.journal.Assignment
import com.booklet.bookletandroid.data.model.booklet.journal.Data
import com.booklet.bookletandroid.data.model.booklet.journal.MarksItem
import com.booklet.bookletandroid.data.model.booklet.journal.SubjectsItem
import com.booklet.bookletandroid.databinding.FragmentCardBinding
import com.booklet.bookletandroid.domain.DateHelper
import com.booklet.bookletandroid.domain.Utils
import com.booklet.bookletandroid.domain.model.Time
import com.booklet.bookletandroid.domain.px
import kotlinx.android.synthetic.main.fragment_card.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.*

class NewScheduleCardFragment : Fragment() {
    private lateinit var mViewModel: NewScheduleCardViewModel
    private lateinit var mBinding: FragmentCardBinding

    var mDate: String = "12.12.12"

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

        val view = mBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val params = cardList.layoutParams as LinearLayout.LayoutParams
        params.setMargins(0, 118.px, 0, 0)
        cardList.layoutParams = params
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onReceivedDataEvent(data: Data) {
        Log.d("Kek", "Kek")

        // data.days!!.filter { it!!.date == requireArguments().getString(ARGUMENT_DATE) }[0]

        paintSchedule(data.days!![0]!!.subjects!!)
    }

    private fun setEmptyContentLayout() {
        cardHeader.text = "Уроков нет"
    }

    private fun paintSchedule(list: List<SubjectsItem?>) {
        doAsync {
            if (list.isEmpty())
                uiThread { setEmptyContentLayout() }

            val height = 12.px

            val addableView = LinearLayout(context)
            addableView.orientation = LinearLayout.VERTICAL

            var counter = 0
            var previous = list[0]
            val layoutInflater = LayoutInflater.from(context)
            val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

            for (i in list) {
                counter++
                layoutParams.setMargins(0, 0, 0, height)

                val local = layoutInflater
                        .inflate(R.layout.item_schedule, null, false)
                local.findViewById<TextView>(R.id.scheduleItemIndex).text = counter.toString()
                local.findViewById<TextView>(R.id.scheduleItemSubject).text = i?.name ?: ""

                val homeworkDescription = prepareHomeworkView(local, i!!.assignments)
                prepareLinkView(local, homeworkDescription)
                prepareMarksView(local, i.marks ?: arrayListOf())
                prepareTimeView(local, counter, i.time!![0]!!, i.time[1]!!, previous!!.time!![1]!!)

                local.setOnLongClickListener {
                    Utils.copyToClipboard(requireContext(), "Д/З", homeworkDescription)
                    requireContext().toast("Д/З скопировано")
                    true
                }

                previous = i
                addableView.addView(local, layoutParams)
            }

            uiThread { setContentLayout(addableView) }
        }
    }

    private fun prepareHomeworkView(local: View, assignments: List<Assignment?>?): String {
        val homeworkView = local.findViewById<TextView>(R.id.scheduleItemHomework)

        // TODO: Вынести блок кода получения текста Д/З.
        val homeworkDescription = if (assignments != null) {
            assignments[0]!!.text ?: ""
        } else {
            ""
        }

        if (homeworkDescription.isEmpty()) {
            homeworkView.visibility = View.GONE
        } else {
            homeworkView.text = homeworkDescription
        }

        return homeworkDescription
    }

    private fun prepareLinkView(local: View, homeworkDescription: String) {
        val linkView = local.findViewById<ImageView>(R.id.scheduleItemLink)
        val urls = Utils.extractUrls(homeworkDescription)
        if (urls.isNotEmpty()) {
            linkView.setOnClickListener {
                requireContext().browse(urls[0])
            }
            linkView.visibility = View.VISIBLE
        }
    }

    private fun prepareMarksView(local: View, marks: List<MarksItem?>) {
        val marksView: LinearLayout = local.findViewById(R.id.scheduleMarks)

        var isCoefficients = false
        for ((index, mark) in marks.withIndex()) {
            val view = LayoutInflater.from(requireContext())
                    .inflate(R.layout.layout_mark, null, false) as RelativeLayout
            val markView: TextView = view.find(R.id.scheduleItemMark)
            val weightView: TextView = view.find(R.id.scheduleItemMarkWeight)
            val param = RelativeLayout.LayoutParams(22.px, 22.px)
            if (index < marks.size) {
                param.marginEnd = 4.px
            }
            markView.layoutParams = param

            // TODO: Реализовать !mark.isPoint
            if (true) {
                markView.text = mark!!.score.toString()
                markView.background = when (mark.score) {
                    "5" -> ContextCompat.getDrawable(requireContext(), R.drawable.background_mark_five)
                    "4" -> ContextCompat.getDrawable(requireContext(), R.drawable.background_mark_four)
                    "3" -> ContextCompat.getDrawable(requireContext(), R.drawable.background_mark_three)
                    else -> ContextCompat.getDrawable(requireContext(), R.drawable.background_mark_two)
                }

                if (mark.weight!! > 1) {
                    weightView.text = mark.weight.toString()
                    param.marginEnd = (-6).px
                    isCoefficients = true
                } else {
                    weightView.visibility = View.GONE
                }
            } else {
                markView.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_point)
                val params = RelativeLayout.LayoutParams(8.px, 8.px)
                params.marginEnd = 24.px
                markView.layoutParams = params
            }

            marksView.addView(view)
        }
        if (marks.isEmpty())
            marksView.visibility = View.GONE
        if (isCoefficients)
            marksView.topPadding = 8.px
    }

    private fun prepareTimeView(local: View,
                                lessonNumber: Int,
                                beginTimeString: String,
                                endTimeString: String,
                                previousEndTimeString: String) {
        val timeView = local.findViewById<TextView>(R.id.scheduleItemTime)
        val beginTime = Time(beginTimeString.split(":").map { it.toInt() }[0], 12)
        val endTime = Time(endTimeString.split(":").map { it.toInt() }[0], 12)
        val previousEndTime = Time(previousEndTimeString.split(":").map { it.toInt() }[0], 12)

        val timeText = "${beginTime.hour.toString().padStart(2, '0')}:" +
                "${beginTime.minute.toString().padStart(2, '0')}-" +
                "${endTime.hour.toString().padStart(2, '0')}:" +
                "${endTime.minute.toString().padStart(2, '0')}"
        val currentDate = DateHelper.getDate().format("DD.MM.YYYY")

        if (currentDate == mDate && DateHelper.isTimeInInterval(DateHelper.getCurrentTime(),
                        beginTime,
                        endTime)) {
            timeView.background = ContextCompat
                    .getDrawable(requireContext(), R.drawable.background_current_lesson)
            timeView.textColor = Color.WHITE
            val paddingDp = 6.px
            timeView.setPadding(paddingDp, 0, paddingDp, 0)
            timeView.text = "$timeText — ${requireContext().getString(R.string.currentLesson)}"
        } else if (currentDate == mDate && lessonNumber > 1 &&
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