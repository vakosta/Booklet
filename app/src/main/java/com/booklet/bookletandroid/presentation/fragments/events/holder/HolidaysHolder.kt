package com.booklet.bookletandroid.presentation.fragments.events.holder

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.booklet.bookletandroid.R
import kotterknife.bindView

class HolidaysHolder(v: View) : EventHolder(v) {
    private val mIcon: ImageView by bindView(R.id.eventIcon)
    private val mText: TextView by bindView(R.id.eventText)
    private val mDate: TextView by bindView(R.id.eventDate)

    override fun bind(text: String, date: String) {
        val textWithFires = "\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25$text"
        mText.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(textWithFires, HtmlCompat.FROM_HTML_MODE_LEGACY)
        else
            Html.fromHtml(textWithFires)
        mDate.text = date
    }
}