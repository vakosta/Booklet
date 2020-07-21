package com.booklet.bookletandroid.presentation.fragments.newschedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.booklet.bookletandroid.R
import com.booklet.bookletandroid.domain.model.Date

class WeekdaysAdapter : RecyclerView.Adapter<WeekdayHolder>() {
    private var activeElement = itemCount / 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekdayHolder {
        return WeekdayHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_weekday,
                        parent,
                        false))
    }

    override fun onBindViewHolder(holder: WeekdayHolder, position: Int) {
        val date = Date() + (position - itemCount / 2)
        holder.bind(date, date == Date())
    }

    override fun getItemCount(): Int = 15000
}