package com.booklet.bookletandroid.presentation.fragments.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.booklet.bookletandroid.R
import com.booklet.bookletandroid.presentation.model.Event

class EventsAdapter(
        private val mEvents: List<com.booklet.bookletandroid.data.model.booklet.events.Event>
) : RecyclerView.Adapter<EventHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        return when (viewType) {
            Event.Type.GRADE.code ->
                GradeHolder(LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_event_default,
                                parent,
                                false))
            Event.Type.HOLIDAYS.code ->
                HolidaysHolder(LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_event_default,
                                parent,
                                false))
            Event.Type.NEW_USER.code ->
                NewUserHolder(LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_event_colored,
                                parent,
                                false))
            else ->
                TestHolder(LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_event_default,
                                parent,
                                false))
        }
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.bind(mEvents[position].text!!, mEvents[position].date!!)
    }

    override fun getItemCount(): Int = mEvents.size

    override fun getItemViewType(position: Int): Int {
        return when (mEvents[position].type) {
            "CLASS_EVENT" -> 1
            "TEST" -> 2
            "HOLIDAYS" -> 3
            "SUBSCRIPTION" -> 4
            "NEW_MARK" -> 5
            "NEW_USER" -> 6
            else -> 1
        }
    }
}