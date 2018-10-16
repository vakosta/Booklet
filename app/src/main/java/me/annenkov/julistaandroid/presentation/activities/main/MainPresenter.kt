package me.annenkov.julistaandroid.presentation.activities.main

import android.content.Context
import me.annenkov.julistaandroid.R
import me.annenkov.julistaandroid.domain.DateHelper
import me.annenkov.julistaandroid.domain.Preferences
import me.annenkov.julistaandroid.domain.model.Date

class MainPresenter(private val view: MainView, private val mContext: Context) {
    var isToolbarShowing = true

    lateinit var mSelectedDate: Date

    fun init() {
        val date = DateHelper
                .getWorkdayDate(Preferences.getInstance(mContext).getWorkdayCount())
        mSelectedDate = Date(date.day, date.month, date.year)

        view.initToolbar()
        view.initCalendar()
        view.initPager()
        setFragmentByNavigationItem(R.id.navSchedule)
    }

    fun setFragmentByNavigationItem(id: Int) {
        when (id) {
            R.id.navAccount -> {
                view.setFragment(0)
                view.setToolbarText("Аккаунт")
                view.hideMenu()
                view.setContentBelowToolbar(false)
                view.setToolbarIsGone(true)
            }
            R.id.navMarks -> {
                view.setFragment(1)
                view.setToolbarText("Оценки")
                view.hideMenu()
                view.setContentBelowToolbar(false)
                view.setToolbarIsGone(true)
            }
            R.id.navSchedule -> {
                view.setFragment(2)
                view.setToolbarText(DateHelper.getMonthNameByNumber(mSelectedDate.month))
                view.setCalendarMenu()
                view.showMenu()
                view.setContentBelowToolbar(false)
                view.setToolbarIsGone(false)
            }
            R.id.navPlus -> {
                view.setFragment(3)
                view.setToolbarText("Julista Plus")
                view.hideMenu()
                view.setContentBelowToolbar(false)
                view.setToolbarIsGone(true)
            }
            R.id.navSettings -> {
                view.setFragment(4)
                view.setToolbarText("Настройки")
                view.hideMenu()
                view.setContentBelowToolbar(true)
                view.setToolbarIsGone(false)
            }
        }
    }
}