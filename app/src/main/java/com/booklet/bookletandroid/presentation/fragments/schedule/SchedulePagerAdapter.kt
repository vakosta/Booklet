package com.booklet.bookletandroid.presentation.fragments.schedule

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.booklet.bookletandroid.domain.DateHelper
import com.booklet.bookletandroid.presentation.fragments.newschedulecard.NewScheduleCardFragment
import hirondelle.date4j.DateTime

class SchedulePagerAdapter(
        private val fm: FragmentManager,
        private val mContext: Context
) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        val date = getDateByPosition(position)
        return NewScheduleCardFragment.newInstance(position - 5000,
                date.format("DD.MM.YY").toString())
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