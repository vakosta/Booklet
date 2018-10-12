package me.annenkov.julistaandroid.presentation.fragments.markscard

import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.nex3z.flowlayout.FlowLayout
import kotterknife.bindView
import me.annenkov.julistaandroid.R
import me.annenkov.julistaandroid.domain.Utils
import me.annenkov.julistaandroid.domain.px
import me.annenkov.julistaandroid.presentation.model.Progress
import org.jetbrains.anko.textColor

class SubjectHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val mSubjectName: TextView by bindView(R.id.marksItemSubject)
    private val mAvg: TextView by bindView(R.id.marksItemAvg)
    private val mFinalMark: TextView by bindView(R.id.marksItemFinal)
    private val mMarkList: FlowLayout by bindView(R.id.marksMarkList)

    fun bind(progress: Progress) {
        mSubjectName.text = progress.subjectName
        mAvg.text = String.format("%.2f", progress.avgFive)
        prepareFinalMarkView(mFinalMark, progress.finalMark)

        for (mark in progress.marks) {
            val markView = TextView(view.context)
            prepareMarkView(markView, mark.toString())
            mMarkList.addView(markView)
        }
    }

    private fun prepareFinalMarkView(view: TextView, mark: Int?) {
        if (mark != null) {
            view.text = mark.toString()
            view.textSize = 12F
            view.width = 18.px
            view.height = 18.px
            view.gravity = Gravity.CENTER
            view.textColor = Color.WHITE
            view.typeface = Typeface.DEFAULT_BOLD
            view.background = when (mark.toInt()) {
                5 -> ContextCompat.getDrawable(view.context, R.drawable.background_mark_five)
                4 -> ContextCompat.getDrawable(view.context, R.drawable.background_mark_four)
                3 -> ContextCompat.getDrawable(view.context, R.drawable.background_mark_three)
                else -> ContextCompat.getDrawable(view.context, R.drawable.background_mark_two)
            }
        } else {
            view.visibility = View.GONE
        }
    }

    private fun prepareMarkView(view: TextView, mark: String) {
        view.text = mark
        view.textSize = 12F
        view.width = 18.px
        view.height = 18.px
        view.gravity = Gravity.CENTER
        view.textColor = Color.WHITE
        view.typeface = Typeface.DEFAULT_BOLD
        view.background = ContextCompat
                .getDrawable(view.context, R.drawable.background_mark_progress)
    }
}