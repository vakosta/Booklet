package com.booklet.bookletandroid.presentation.fragments.markscard

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.booklet.bookletandroid.R
import com.booklet.bookletandroid.domain.px
import com.booklet.bookletandroid.presentation.model.event.Result
import com.nex3z.flowlayout.FlowLayout
import kotterknife.bindView
import org.jetbrains.anko.textColor

class ResultHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val mSubjectName: TextView by bindView(R.id.marksItemSubject)
    private val mMarkList: FlowLayout by bindView(R.id.marksMarkList)
    private val mFinal: TextView by bindView(R.id.finalMark)

    fun bind(result: Result) {
        mSubjectName.text = result.subjectName

        for (mark in result.marks) {
            val markView = TextView(view.context)
            prepareMarkView(markView, mark.toString())
            mMarkList.addView(markView)
        }

        if (result.finalMark != null)
            prepareFinalMarkView(mFinal, result.finalMark.toString())
    }

    private fun prepareMarkView(view: TextView, mark: String) {
        view.text = mark
        view.textSize = 12F
        view.width = 24.px
        view.height = 24.px
        view.gravity = Gravity.CENTER
        view.textColor = Color.WHITE
        view.typeface = Typeface.DEFAULT_BOLD
        view.background = when (mark.toInt()) {
            5 -> ContextCompat.getDrawable(view.context, R.drawable.background_mark_five)
            4 -> ContextCompat.getDrawable(view.context, R.drawable.background_mark_four)
            3 -> ContextCompat.getDrawable(view.context, R.drawable.background_mark_three)
            else -> ContextCompat.getDrawable(view.context, R.drawable.background_mark_two)
        }
    }

    private fun prepareFinalMarkView(view: TextView, mark: String) {
        view.text = mark
        view.textSize = 12F
        view.width = 24.px
        view.height = 24.px
        view.visibility = View.VISIBLE
        view.gravity = Gravity.CENTER
        view.textColor = Color.WHITE
        view.typeface = Typeface.DEFAULT_BOLD
        view.background = when (mark.toInt()) {
            5 -> ContextCompat.getDrawable(view.context, R.drawable.background_mark_five)
            4 -> ContextCompat.getDrawable(view.context, R.drawable.background_mark_four)
            3 -> ContextCompat.getDrawable(view.context, R.drawable.background_mark_three)
            else -> ContextCompat.getDrawable(view.context, R.drawable.background_mark_two)
        }
    }
}