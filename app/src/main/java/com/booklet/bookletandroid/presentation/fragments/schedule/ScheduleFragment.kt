package com.booklet.bookletandroid.presentation.fragments.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.booklet.bookletandroid.R
import com.booklet.bookletandroid.domain.*
import com.booklet.bookletandroid.domain.model.Refresh
import com.booklet.bookletandroid.presentation.CardBaseView
import com.booklet.bookletandroid.presentation.FragmentBaseView
import com.booklet.bookletandroid.presentation.ViewPagerFragment
import com.booklet.bookletandroid.presentation.customviews.CustomLinearLayout
import com.booklet.bookletandroid.presentation.customviews.RotateDownTransformer
import hirondelle.date4j.DateTime
import kotlinx.android.synthetic.main.layout_week_days.*
import kotterknife.bindView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor

class ScheduleFragment : ViewPagerFragment(), ScheduleView, View.OnClickListener, View.OnTouchListener {
    private lateinit var mPresenter: SchedulePresenter

    private lateinit var mPager: androidx.viewpager.widget.ViewPager
    private lateinit var mPagerAdapter: SchedulePagerAdapter

    private lateinit var mPagerLayout: CustomLinearLayout

    private lateinit var mCurrentDay: TextView
    private val mWeekdays: RelativeLayout by bindView(R.id.weekDays)

    private lateinit var mMonday: LinearLayout
    private lateinit var mTuesday: LinearLayout
    private lateinit var mWednesday: LinearLayout
    private lateinit var mThursday: LinearLayout
    private lateinit var mFriday: LinearLayout
    private lateinit var mSaturday: LinearLayout

    private lateinit var mMondayDate: TextView
    private lateinit var mTuesdayDate: TextView
    private lateinit var mWednesdayDate: TextView
    private lateinit var mThursdayDate: TextView
    private lateinit var mFridayDate: TextView
    private lateinit var mSaturdayDate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = SchedulePresenter(this, requireActivity())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)

        activityEnabled {
            view.find<LinearLayout>(R.id.weekdaySaturday).visibility =
                    if (!Preferences.getInstance(it).saturdayLessons)
                        View.GONE
                    else
                        View.VISIBLE
        }

        mPagerLayout = view.find(R.id.scheduleListPagerLayout)
        mPagerLayout.setOnTouchListener(this)

        mPager = view.find(R.id.scheduleListPager)

        mMonday = view.find(R.id.weekdayMonday)
        mMonday.setOnClickListener(this)
        mMondayDate = view.find(R.id.weekdayMondayDate)

        mTuesday = view.find(R.id.weekdayTuesday)
        mTuesday.setOnClickListener(this)
        mTuesdayDate = view.find(R.id.weekdayTuesdayDate)

        mWednesday = view.find(R.id.weekdayWednesday)
        mWednesday.setOnClickListener(this)
        mWednesdayDate = view.find(R.id.weekdayWednesdayDate)

        mThursday = view.find(R.id.weekdayThursday)
        mThursday.setOnClickListener(this)
        mThursdayDate = view.find(R.id.weekdayThursdayDate)

        mFriday = view.find(R.id.weekdayFriday)
        mFriday.setOnClickListener(this)
        mFridayDate = view.find(R.id.weekdayFridayDate)

        mSaturday = view.find(R.id.weekdaySaturday)
        mSaturday.setOnClickListener(this)
        mSaturdayDate = view.find(R.id.weekdaySaturdayDate)

        mCurrentDay = view.find(R.id.currentDay)
        mCurrentDay.setOnClickListener {
            mPresenter.setPosition(5000)
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun fetchData() {
        mPresenter.init()
        activityEnabled {
            weekdaySaturday.visibility =
                    if (!Preferences.getInstance(it).saturdayLessons)
                        View.GONE
                    else
                        View.VISIBLE
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe
    fun refresh(refresh: Refresh) {
        fetchData()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.weekdayMonday -> mPresenter.setWeekday(1)
            R.id.weekdayTuesday -> mPresenter.setWeekday(2)
            R.id.weekdayWednesday -> mPresenter.setWeekday(3)
            R.id.weekdayThursday -> mPresenter.setWeekday(4)
            R.id.weekdayFriday -> mPresenter.setWeekday(5)
            R.id.weekdaySaturday -> mPresenter.setWeekday(6)
        }
    }

    override fun onTouch(p0: View, p1: MotionEvent): Boolean {
        return if (isAdded) Utils.convertPixelsToDp(requireActivity(), p1.y) > 118
        else true
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDateSet(date: DateTime) {
        mPresenter.setPosition(date)
    }

    override fun initPager() {
        activityEnabled {
            mPagerAdapter = SchedulePagerAdapter(childFragmentManager, it)
            mPresenter.setPosition(5000)
            mPager.adapter = mPagerAdapter
            mPager.setPageTransformer(false, RotateDownTransformer())
            mPager.offscreenPageLimit = 1

            mPager.addOnPageChangeListener(object : androidx.viewpager.widget.ViewPager.OnPageChangeListener {
                override fun onPageSelected(position: Int) {
                    mPresenter.paintWeekByPosition(position)
                    if (mPresenter.index != 5000) showCurrentDay()
                    else hideCurrentDay()
                }

                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }
            })
        }
    }

    override fun setViewPagerItem(item: Int, smooth: Boolean) {
        mPager.post { mPager.setCurrentItem(item, smooth) }
    }

    override fun paintWeekday(weekday: Int) {
        val oldDay: TextView = when (mPresenter.weekDay) {
            1 -> mMondayDate
            2 -> mTuesdayDate
            3 -> mWednesdayDate
            4 -> mThursdayDate
            5 -> mFridayDate
            else -> mSaturdayDate
        }
        oldDay.backgroundResource = 0
        activityEnabled {
            oldDay.textColor = ContextCompat
                    .getColor(it, R.color.colorWeekdayDateName)
        }

        val editableDay: TextView = when (weekday) {
            1 -> mMondayDate
            2 -> mTuesdayDate
            3 -> mWednesdayDate
            4 -> mThursdayDate
            5 -> mFridayDate
            else -> mSaturdayDate
        }
        editableDay.backgroundResource = R.drawable.shape_round
        activityEnabled {
            editableDay.textColor = context!!.attribute(R.attr.colorTextWeekdayActive).data
        }
    }

    override fun paintWeek(week: ArrayList<DateTime>) {
        mMondayDate.text = week[0].day.toString()
        mTuesdayDate.text = week[1].day.toString()
        mWednesdayDate.text = week[2].day.toString()
        mThursdayDate.text = week[3].day.toString()
        mFridayDate.text = week[4].day.toString()
        mSaturdayDate.text = week[5].day.toString()
    }

    override fun paintCurrentDay() {
        activityEnabled {
            mCurrentDay.text = DateHelper
                    .getWorkdayDate(Preferences.getInstance(it)
                            .getWorkdayCount()).day.toString()
        }
    }

    override fun showCurrentDay() {
        if (!mPresenter.isCurrentDayShowing) {
            mCurrentDay.animate()
                    .translationX(32.px.toFloat())
                    .duration = FragmentBaseView.DURATION
            mCurrentDay.invalidate()
            mPresenter.isCurrentDayShowing = true
        }
    }

    override fun hideCurrentDay() {
        if (mPresenter.isCurrentDayShowing) {
            mCurrentDay.animate()
                    .translationX(0F)
                    .duration = FragmentBaseView.DURATION
            mPresenter.isCurrentDayShowing = false
        }
    }

    override fun showWeekdays() {
        if (!mPresenter.isWeekdaysShowing) {
            mWeekdays.animate()
                    .alpha(1F)
                    .duration = FragmentBaseView.DURATION
            mPresenter.isWeekdaysShowing = true
        }
    }

    override fun hideWeekdays() {
        if (mPresenter.isWeekdaysShowing) {
            mWeekdays.animate()
                    .alpha(0F)
                    .duration = FragmentBaseView.DURATION
            mPresenter.isWeekdaysShowing = false
        }
    }

    @Subscribe
    override fun onScroll(position: CardBaseView.ScrollPosition) {
        super.onScroll(position)
    }

    override fun showPeriodBar() {
        if (mPresenter.index != 5000)
            showCurrentDay()
        showWeekdays()
    }

    override fun hidePeriodBar() {
        hideCurrentDay()
        hideWeekdays()
    }
}