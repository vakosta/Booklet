package com.booklet.bookletandroid.presentation.fragments.newschedule

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.booklet.bookletandroid.R
import com.booklet.bookletandroid.domain.model.Date
import kotterknife.bindView
import org.jetbrains.anko.textColor

class WeekdayHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val mDayOfWeek: TextView by bindView(R.id.dayOfWeek)
    private val mDayOfMonth: TextView by bindView(R.id.dayOfMonth)

    fun bind(date: Date, isActive: Boolean) {
        if (isActive)
            activate()

        mDayOfWeek.text = date.getDayOfWeek()
        mDayOfMonth.text = date.day.toString()
    }

    fun activate() {
        view.background = ContextCompat.getDrawable(view.context,
                R.drawable.background_item_weekday_active)

        mDayOfWeek.textColor = Color.WHITE
        mDayOfMonth.textColor = Color.WHITE
    }
}