package me.annenkov.julistaandroid.presentation.fragments.schedule

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import me.annenkov.julistaandroid.domain.DateHelper
import me.annenkov.julistaandroid.domain.Preferences
import me.annenkov.julistaandroid.presentation.fragments.schedulecard.ScheduleCardFragment

class SchedulePagerAdapter(
        private val fm: FragmentManager,
        private val mContext: Context
) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
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
        return FragmentStatePagerAdapter.POSITION_NONE
    }

    override fun getCount(): Int = 10000
}