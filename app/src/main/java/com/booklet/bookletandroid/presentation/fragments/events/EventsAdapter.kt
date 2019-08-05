package com.booklet.bookletandroid.presentation.fragments.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.booklet.bookletandroid.R
import com.booklet.bookletandroid.presentation.model.Event

class EventsAdapter(
        private val mEvents: List<Event>
) : RecyclerView.Adapter<EventHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val layout = when (viewType) {
            Event.Type.GRADE.code ->
                R.layout.item_event_default
            else ->
                R.layout.item_event_default
        }

        return GradeHolder(LayoutInflater
                .from(parent.context)
                .inflate(layout,
                        parent,
                        false))
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.bind(mEvents[position].text, mEvents[position].date)
    }

    override fun getItemCount(): Int = mEvents.size
}