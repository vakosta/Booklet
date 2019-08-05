package com.booklet.bookletandroid.presentation.fragments.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.booklet.bookletandroid.R
import com.booklet.bookletandroid.presentation.ViewPagerFragment
import com.booklet.bookletandroid.presentation.model.Event
import kotlinx.android.synthetic.main.fragment_events.*

class EventsFragment : ViewPagerFragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun fetchData() {
        initRecyclerView(listOf(
                Event(Event.Type.GRADE, "Сегодня в классе кто-то получил пятёрку!", "Сегодня"),
                Event(Event.Type.GRADE, "Ебой, это ещё одно событие какое-то, лол", "Сегодня"),
                Event(Event.Type.GRADE, "Hello", "Сегодня"),
                Event(Event.Type.GRADE, "Hello", "Сегодня"),
                Event(Event.Type.GRADE, "Hello", "Сегодня"),
                Event(Event.Type.GRADE, "Hello", "Сегодня"),
                Event(Event.Type.GRADE, "Hello", "Сегодня"),
                Event(Event.Type.GRADE, "Hello", "Сегодня"),
                Event(Event.Type.GRADE, "Hello", "Сегодня"),
                Event(Event.Type.GRADE, "Hello", "Сегодня")
        ))
    }

    fun initRecyclerView(events: List<Event>) {
        eventsRecyclerView.layoutManager = LinearLayoutManager(activity!!)
        eventsRecyclerView.setHasFixedSize(true)
        eventsRecyclerView.adapter = EventsAdapter(events)
        eventsRecyclerView.setItemViewCacheSize(30)
    }
}