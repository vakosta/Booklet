package me.annenkov.julistaandroid.presentation.fragments.markscard

import android.content.Context
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
import me.annenkov.julistaandroid.domain.px
import me.annenkov.julistaandroid.presentation.model.Progress
import org.jetbrains.anko.leftPadding
import org.jetbrains.anko.rightPadding
import org.jetbrains.anko.textColor

class SubjectHolder(val view: View, val mContext: Context) : RecyclerView.ViewHolder(view) {
    private val mPurposeLimit = 0.4
    private val mPurposeExtraLimit = 0.5

    private val mSubjectName: TextView by bindView(R.id.marksItemSubject)
    private val mAvg: TextView by bindView(R.id.marksItemAvg)
    private val mFinalMark: TextView by bindView(R.id.marksItemFinal)
    private val mMarkList: FlowLayout by bindView(R.id.marksMarkList)
    private val mPurpose: TextView by bindView(R.id.purpose)

    fun bind(progress: Progress, purpose: Int) {
        mSubjectName.text = progress.subjectName
        prepareAvgView(mAvg, progress.avgFive, purpose)
        prepareFinalMarkView(mFinalMark, progress.finalMark)

        for (mark in progress.marks) {
            val markView = TextView(view.context)
            prepareMarkView(markView, mark.toString())
            mMarkList.addView(markView)
        }

        preparePurpose(mPurpose, purpose, progress.avgFive, progress.marks)
    }

    private fun prepareAvgView(view: TextView, avg: Double, purpose: Int) {
        view.text = String.format("%.2f", avg)
        if (avg < purpose - mPurposeExtraLimit) {
            view.textColor = Color.WHITE
            view.background = ContextCompat
                    .getDrawable(mContext, R.drawable.background_avg_alert)
            view.leftPadding = 6.px
            view.rightPadding = 6.px
        } else if (avg < purpose - mPurposeLimit) {
            view.textColor = ContextCompat
                    .getColor(mContext, R.color.colorPurposeLimit)
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

    private fun preparePurpose(view: TextView, purpose: Int, avg: Double, marks: List<Int>) {
        if (avg > purpose - mPurposeLimit) {
            view.visibility = View.GONE
        } else {
            var targetAvg = avg
            var count = 0
            while (targetAvg < purpose - mPurposeLimit) {
                count += 1
                val marksSum = marks.sum() + 5 * count
                val marksCount = marks.size + count
                targetAvg = marksSum.toDouble() / marksCount
            }
            view.text = "Получите $count пятёрок"
        }
    }
}