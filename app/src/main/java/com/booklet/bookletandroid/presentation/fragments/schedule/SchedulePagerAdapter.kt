package com.booklet.bookletandroid.presentation.fragments.schedule

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.booklet.bookletandroid.domain.DateHelper
import com.booklet.bookletandroid.presentation.fragments.schedulecard.ScheduleCardFragment
import hirondelle.date4j.DateTime

class SchedulePagerAdapter(
        private val fm: FragmentManager,
        private val mContext: Context,
        private val scheduleCardCallback: ScheduleCardFragment.ScheduleDataListener
) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        val date = getDateByPosition(position)
        val fragment = ScheduleCardFragment.newInstance(position - 5000,
                date.format("DD.MM.YY").toString())

        fragment.setScheduleDataListener(scheduleCardCallback)

        return fragment
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun getCount(): Int = COUNT

    companion object {
        const val COUNT = 10000
        const val CENTRAL_POSITION = COUNT / 2

        fun getDateByPosition(position: Int): DateTime {
            return DateHelper.getDate().plusDays(position)
        }
    }
}