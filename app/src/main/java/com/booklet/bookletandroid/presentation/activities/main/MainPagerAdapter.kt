package com.booklet.bookletandroid.presentation.activities.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.booklet.bookletandroid.presentation.fragments.account.AccountFragment
import com.booklet.bookletandroid.presentation.fragments.events.EventsFragment
import com.booklet.bookletandroid.presentation.fragments.marks.MarksFragment
import com.booklet.bookletandroid.presentation.fragments.plus.PlusFragment
import com.booklet.bookletandroid.presentation.fragments.schedule.ScheduleFragment
import com.booklet.bookletandroid.presentation.fragments.settings.SettingsFragment

class MainPagerAdapter(fm: androidx.fragment.app.FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> AccountFragment()
            1 -> MarksFragment()
            2 -> ScheduleFragment()
            3 -> PlusFragment()
            4 -> SettingsFragment()
            else -> EventsFragment()
        }
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun getCount(): Int = 6
}