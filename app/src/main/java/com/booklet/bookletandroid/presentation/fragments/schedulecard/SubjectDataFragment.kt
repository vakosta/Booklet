package com.booklet.bookletandroid.presentation.fragments.schedulecard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.booklet.bookletandroid.R
import com.booklet.bookletandroid.presentation.model.schedule.Subject
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_subject_data.*
import org.jetbrains.anko.backgroundResource

class SubjectDataFragment : BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_subject_data, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val subject = requireArguments().getSerializable(ARGUMENT_SUBJECT) as Subject
        subject.let {
            title.text = it.name

            if (it.marks.isNotEmpty()) {
                mark1ImageView.backgroundResource = getMarkResource(it.marks[0].toString())
                mark1TextView.text = "Работа в классе"
            } else
                groupMark1.visibility = View.GONE

            if (it.marks.size > 1) {
                mark2ImageView.backgroundResource = getMarkResource(it.marks[1].toString())
                mark2TextView.text = "Работа в классе"
            } else
                groupMark2.visibility = View.GONE

            if (it.marks.size > 2) {
                mark2ImageView.backgroundResource = getMarkResource(it.marks[2].toString())
                mark2TextView.text = "Работа в классе"
            } else
                groupMark3.visibility = View.GONE

            if (it.marks.isEmpty())
                groupMarks.visibility = View.GONE

            if (it.teacherComment != null)
                teacherComment.text = it.teacherComment
            else
                groupComment.visibility = View.GONE

            if (it.homeworks.isNotEmpty() && it.homeworks[0].text != null)
                homeworkText.text = it.homeworks[0].text
            else
                groupHomework.visibility = View.GONE

            if (it.teacher != null)
                teacherName.text = it.teacher
            else
                groupTeacher.visibility = View.GONE

            if (it.room != null)
                room.text = it.room
            else
                groupRoom.visibility = View.GONE

            if (it.lessonTopic != null)
                topic.text = it.lessonTopic
            else
                groupTopic.visibility = View.GONE

            if (it.lessonModule != null)
                module.text = it.lessonModule
            else
                groupModule.visibility = View.GONE
        }
    }

    private fun getMarkResource(mark: String): Int {
        return when (mark) {
            "5" -> R.drawable.prefs_five
            "4" -> R.drawable.prefs_four
            "3" -> R.drawable.prefs_three
            "2" -> R.drawable.background_mark_two
            "1" -> R.drawable.background_mark_two
            else -> R.drawable.background_point
        }
    }

    companion object {
        private val TAG = this::class.java.simpleName

        const val ARGUMENT_SUBJECT = "arg_subject"

        fun newInstance(subject: Subject): SubjectDataFragment {
            val fragment = SubjectDataFragment()
            val arguments = Bundle()
            arguments.putSerializable(ARGUMENT_SUBJECT, subject)
            fragment.arguments = arguments
            return fragment
        }
    }
}