package com.booklet.bookletandroid.presentation.fragments.newschedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.booklet.bookletandroid.R
import com.booklet.bookletandroid.databinding.FragmentScheduleBinding
import com.booklet.bookletandroid.domain.Utils
import com.booklet.bookletandroid.presentation.ViewPagerFragment
import com.booklet.bookletandroid.presentation.customviews.RotateDownTransformer
import com.booklet.bookletandroid.presentation.fragments.schedule.SchedulePagerAdapter
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.layout_week_days.*

class NewScheduleFragment : ViewPagerFragment(), View.OnClickListener, View.OnTouchListener {
    protected lateinit var mViewModel: NewScheduleViewModel
    protected lateinit var mBinding: FragmentScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mViewModel = ViewModelProvider(this).get(NewScheduleViewModel::class.java)
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_schedule,
                container,
                false)
        mBinding.viewModel = mViewModel

        val view = mBinding.root

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        weekdayMonday.setOnClickListener(this)
        weekdayTuesday.setOnClickListener(this)
        weekdayWednesday.setOnClickListener(this)
        weekdayThursday.setOnClickListener(this)
        weekdayFriday.setOnClickListener(this)
        weekdaySaturday.setOnClickListener(this)

        currentDay.setOnClickListener {
            // TODO: Implement this method.
        }

        initPager()
    }

    fun setupListeners() {
        weekdayMonday.setOnClickListener(this)
    }

    override fun fetchData() {
    }

    fun initPager() {
        activityEnabled {
            // mPresenter.setPosition(5000)
            scheduleListPager.adapter = SchedulePagerAdapter(childFragmentManager, it)
            scheduleListPager.setPageTransformer(false, RotateDownTransformer())
            scheduleListPager.offscreenPageLimit = 1

            scheduleListPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageSelected(position: Int) {
                    // TODO: Implement this method.
                }

                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int,
                                            positionOffset: Float,
                                            positionOffsetPixels: Int) {
                }
            })
        }
    }

    override fun onClick(v: View) {
        mViewModel.currentWeekday = when (v.id) {
            R.id.weekdayMonday -> NewScheduleViewModel.Weekday.MONDAY
            R.id.weekdayTuesday -> NewScheduleViewModel.Weekday.TUESDAY
            R.id.weekdayWednesday -> NewScheduleViewModel.Weekday.WEDNESDAY
            R.id.weekdayThursday -> NewScheduleViewModel.Weekday.THURSDAY
            R.id.weekdayFriday -> NewScheduleViewModel.Weekday.FRIDAY
            else -> NewScheduleViewModel.Weekday.SATURDAY
        }
    }

    override fun onTouch(p0: View, p1: MotionEvent): Boolean {
        return if (isAdded) Utils.convertPixelsToDp(requireActivity(), p1.y) > 118
        else true
    }
}