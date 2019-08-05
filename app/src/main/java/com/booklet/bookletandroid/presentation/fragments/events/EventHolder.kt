package com.booklet.bookletandroid.presentation.fragments.events

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class EventHolder(val view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(text: String, date: String)
}