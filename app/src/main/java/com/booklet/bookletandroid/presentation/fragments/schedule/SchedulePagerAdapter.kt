package com.booklet.bookletandroid.presentation.fragments.schedule

import android.content.Context
import com.booklet.bookletandroid.domain.DateHelper
import com.booklet.bookletandroid.domain.Preferences
import com.booklet.bookletandroid.presentation.fragments.schedulecard.ScheduleCardFragment

class SchedulePagerAdapter(
        private val fm: androidx.fragment.app.FragmentManager,
        private val mContext: Context
) : androidx.fragment.app.FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        // TODO: Refactor this code
        val currentDate = DateHelper
                .getWorkdayDate(Preferences.getInstance(mContext).getWorkdayCount())
        val date = currentDate.plusDays(DateHelper.getAllDaysByBusinessDays((
                DateHelper.getWeekday(currentDate)),
                (if (position <= 5) 5000 else position) - 5000,
                Preferences.getInstance(mContext).getWorkdayCount()
        ))

        return ScheduleCardFragment.newInstance(position - 5000,
                date.format("DD.MM.YY").toString())
    }

    override fun getItemPosition(`object`: Any): Int {
        return androidx.fragment.app.FragmentStatePagerAdapter.POSITION_NONE
    }

    override fun getCount(): Int = 10000
}