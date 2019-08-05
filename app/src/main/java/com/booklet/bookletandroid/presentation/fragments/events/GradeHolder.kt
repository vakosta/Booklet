package com.booklet.bookletandroid.presentation.fragments.events

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.booklet.bookletandroid.R
import kotterknife.bindView

class GradeHolder(val v: View) : EventHolder(v) {
    private val mIcon: ImageView by bindView(R.id.eventIcon)
    private val mText: TextView by bindView(R.id.eventText)
    private val mDate: TextView by bindView(R.id.eventDate)

    override fun bind(text: String, date: String) {
        mText.text = text
        mDate.text = date
    }
}