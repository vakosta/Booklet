package com.booklet.bookletandroid.presentation.fragments.events

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.booklet.bookletandroid.R
import kotterknife.bindView

class NewUserHolder(v: View) : EventHolder(v) {
    private val mBackground: LinearLayout by bindView(R.id.eventBackground)
    private val mText: TextView by bindView(R.id.eventText)
    private val mDate: TextView by bindView(R.id.eventDate)

    override fun bind(text: String, date: String) {
        mText.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
        else
            Html.fromHtml(text)
        mDate.text = date
    }
}