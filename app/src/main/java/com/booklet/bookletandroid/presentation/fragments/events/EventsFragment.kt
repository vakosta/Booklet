package com.booklet.bookletandroid.presentation.fragments.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.booklet.bookletandroid.R
import com.booklet.bookletandroid.domain.Preferences
import com.booklet.bookletandroid.presentation.ViewPagerFragment
import com.booklet.bookletandroid.presentation.model.Event
import com.booklet.bookletandroid.presentation.model.Filter
import kotlinx.android.synthetic.main.fragment_events.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class EventsFragment : ViewPagerFragment() {
    val allItems = arrayListOf(
            Event(1, Event.Type.GRADE, "Сегодня в классе кто-то получил <b>пятёрку</b>!", "Сегодня"),
            Event(2, Event.Type.GRADE, "Ебой, это ещё одно событие какое-то, лол", "Сегодня"),
            Event(3, Event.Type.NEW_MARK, "Вы получили новую оценку!", "Сегодня"),
            Event(4, Event.Type.GRADE, "Hello", "Сегодня"),
            Event(5, Event.Type.HOLIDAYS, "Ура, каникулы уже завтра!", "Сегодня"),
            Event(6, Event.Type.NEW_MARK, "Вы получили новую оценку!", "Сегодня"),
            Event(7, Event.Type.GRADE, "Hello", "Сегодня"),
            Event(8, Event.Type.TEST, "Завтра состоится <b>контрольная работа</b> по предмету <b>ОБЖ</b>", "Сегодня"),
            Event(9, Event.Type.GRADE, "Hello", "Сегодня"),
            Event(10, Event.Type.NEW_USER, "Какой-то чел присоединился к нашему дневнику!", "Сегодня"),
            Event(11, Event.Type.HOLIDAYS, "Завтра праздник :)", "Сегодня"),
            Event(12, Event.Type.GRADE, "Hello", "Сегодня"),
            Event(13, Event.Type.GRADE, "Hello", "Сегодня")
    )
    var filteredItems = arrayListOf<Event>()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun fetchData() {
        filteredItems.addAll(allItems)
        initRecyclerView(filteredItems)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    private fun initRecyclerView(events: List<Event>) {
        eventsRecyclerView.layoutManager = LinearLayoutManager(activity!!)
        eventsRecyclerView.setHasFixedSize(true)
        eventsRecyclerView.adapter = EventsAdapter(events)
        eventsRecyclerView.setItemViewCacheSize(30)
    }

    @Subscribe
    fun onFilter(filter: Filter) {
        val prefs = Preferences.getInstance(activity!!)
        val newItems = allItems.filter {
            (prefs.filterGrade || (it.type != Event.Type.GRADE && it.type != Event.Type.NEW_USER)) &&
                    (prefs.filterNewMarks || it.type != Event.Type.NEW_MARK) &&
                    (prefs.filterTests || it.type != Event.Type.TEST) &&
                    (prefs.filterHolidays || it.type != Event.Type.HOLIDAYS)
        }
        val productDiffUtilCallback = EventDiffUtilCallback(filteredItems, newItems)
        val productDiffResult = DiffUtil.calculateDiff(productDiffUtilCallback)
        filteredItems.clear()
        filteredItems.addAll(newItems)
        productDiffResult.dispatchUpdatesTo(eventsRecyclerView.adapter!!)
    }
}