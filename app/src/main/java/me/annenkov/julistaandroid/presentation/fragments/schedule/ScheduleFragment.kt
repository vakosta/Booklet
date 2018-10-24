package me.annenkov.julistaandroid.presentation.fragments.schedule

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import hirondelle.date4j.DateTime
import kotlinx.android.synthetic.main.layout_week_days.*
import kotterknife.bindView
import me.annenkov.julistaandroid.R
import me.annenkov.julistaandroid.domain.DateHelper
import me.annenkov.julistaandroid.domain.Preferences
import me.annenkov.julistaandroid.domain.Utils
import me.annenkov.julistaandroid.domain.px
import me.annenkov.julistaandroid.presentation.CardBaseView
import me.annenkov.julistaandroid.presentation.FragmentBaseView
import me.annenkov.julistaandroid.presentation.ViewPagerFragment
import me.annenkov.julistaandroid.presentation.customviews.CustomLinearLayout
import me.annenkov.julistaandroid.presentation.customviews.RotateDownTransformer
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor

class ScheduleFragment : ViewPagerFragment(), ScheduleView, View.OnClickListener, View.OnTouchListener {
    private lateinit var mPresenter: SchedulePresenter

    private var mHasInflated = false

    private lateinit var mPager: ViewPager
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
        mPresenter = SchedulePresenter(this, activity!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)

        view.find<LinearLayout>(R.id.weekdaySaturday).visibility =
                if (!Preferences.getInstance(activity!!).saturdayLessons)
                    View.GONE
                else
                    View.VISIBLE

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

    override fun fetchData() {
        mPresenter.init()
        weekdaySaturday.visibility =
                if (!Preferences.getInstance(activity!!).saturdayLessons)
                    View.GONE
                else
                    View.VISIBLE
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(p0: View, p1: MotionEvent): Boolean {
        return Utils.convertPixelsToDp(activity!!, p1.y) > 118
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDateSet(date: DateTime) {
        mPresenter.setPosition(date)
    }

    override fun initPager() {
        mPagerAdapter = SchedulePagerAdapter(childFragmentManager, activity!!)
        mPresenter.setPosition(5000)
        mPager.adapter = mPagerAdapter
        mPager.setPageTransformer(false, RotateDownTransformer())
        mPager.offscreenPageLimit = 1

        mPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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
        oldDay.textColor = ContextCompat
                .getColor(activity!!, R.color.colorWeekdayDateName)

        val editableDay: TextView = when (weekday) {
            1 -> mMondayDate
            2 -> mTuesdayDate
            3 -> mWednesdayDate
            4 -> mThursdayDate
            5 -> mFridayDate
            else -> mSaturdayDate
        }
        editableDay.backgroundResource = R.drawable.shape_round
        editableDay.textColor = ContextCompat
                .getColor(activity!!, R.color.colorWeekdayDateNameActive)
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
        mCurrentDay.text = DateHelper
                .getWorkdayDate(Preferences.getInstance(activity!!)
                        .getWorkdayCount()).day.toString()
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