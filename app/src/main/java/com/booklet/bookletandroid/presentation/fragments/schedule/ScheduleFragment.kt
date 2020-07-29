package com.booklet.bookletandroid.presentation.fragments.schedule

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.viewpager.widget.ViewPager
import com.booklet.bookletandroid.R
import com.booklet.bookletandroid.databinding.FragmentScheduleBinding
import com.booklet.bookletandroid.domain.Utils
import com.booklet.bookletandroid.domain.model.Date
import com.booklet.bookletandroid.domain.model.Result
import com.booklet.bookletandroid.presentation.ViewPagerFragment
import com.booklet.bookletandroid.presentation.customviews.RotateDownTransformer
import com.booklet.bookletandroid.presentation.fragments.schedulecard.ScheduleCardFragment
import com.booklet.bookletandroid.presentation.model.event.SelectWeekdayEvent
import com.booklet.bookletandroid.presentation.model.event.SelectedPage
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.layout_week_days.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import kotlin.math.abs

class ScheduleFragment : ViewPagerFragment(), View.OnClickListener, View.OnTouchListener,
        ScheduleCardFragment.ScheduleDataListener {
    private lateinit var mViewModel: ScheduleViewModel
    private lateinit var mBinding: FragmentScheduleBinding

    private var mIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mViewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)
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

        setupObservers()
        // setupListeners()
        initWeekdaysViewPager()
        initPager()
        setPagerPosition(scheduleListPager.adapter!!.count / 2)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        EventBus.getDefault().register(this)
        Log.d(TAG, "EventBus инициализирован.")
    }

    override fun onDetach() {
        EventBus.getDefault().unregister(this)
        Log.d(TAG, "EventBus деинициализирован.")

        super.onDetach()
    }

    override fun fetchData() {
    }

    private fun setupObservers() {
        mViewModel.scheduleLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when (result.status) {
                    Result.Status.SUCCESS -> {
                        EventBus.getDefault().post(result.data)
                    }

                    Result.Status.ERROR -> {
                    }

                    Result.Status.LOADING -> {
                    }
                }
            }
        })
    }

    private fun setupListeners() {
        weekdayMonday.setOnClickListener(this)
        weekdayTuesday.setOnClickListener(this)
        weekdayWednesday.setOnClickListener(this)
        weekdayThursday.setOnClickListener(this)
        weekdayFriday.setOnClickListener(this)
        weekdaySaturday.setOnClickListener(this)

        currentDay.setOnClickListener {
            setPagerPosition(SchedulePagerAdapter.CENTRAL_POSITION)
        }
    }

    private fun initWeekdaysViewPager() {
        activityEnabled {
            val adapter = WeekdaysAdapter()
            val snapHelper = LinearSnapHelper()

            weekdaysRecyclerView.layoutManager = CenterLayoutManager(requireActivity(),
                    LinearLayoutManager.HORIZONTAL, false)
            snapHelper.attachToRecyclerView(weekdaysRecyclerView)
            weekdaysRecyclerView.attachSnapHelperWithListener(snapHelper,
                    SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL,
                    object : OnSnapPositionChangeListener {
                        override fun onSnapPositionChange(position: Int) {
                            EventBus.getDefault().post(Date() + (position -
                                    adapter.itemCount / 2))
                        }
                    })
            weekdaysRecyclerView.adapter = adapter
            weekdaysRecyclerView.setHasFixedSize(true)
            weekdaysRecyclerView.setItemViewCacheSize(50)
        }
    }

    private fun initPager() {
        activityEnabled {
            val adapter = SchedulePagerAdapter(childFragmentManager, it, this)

            scheduleListPager.adapter = adapter
            scheduleListPager.setPageTransformer(false, RotateDownTransformer())
            scheduleListPager.offscreenPageLimit = 1
            scheduleListPager.setPadding(-11, 0, -15, 0)
            scheduleListPager.clipToPadding = false

            scheduleListPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageSelected(position: Int) {
                    // TODO: Implement (or refactor) this method.

                    // mViewModel.getSchedule("12.07.2020", "12.07.2020")
                    setWeekdaysRecyclerViewPosition(position)
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

    @Subscribe
    fun onSelectWeekdayEvent(event: SelectWeekdayEvent) {
        setPagerPosition(event.position)
    }

    @Subscribe
    fun onSelectPage(page: SelectedPage) {
        setPagerPosition(SchedulePagerAdapter.CENTRAL_POSITION)
    }

    private fun setPagerPosition(position: Int) {
        scheduleListPager.setCurrentItem(position, abs(mIndex - position) <= 8)
        mIndex = position
    }

    private fun setWeekdaysRecyclerViewPosition(position: Int) {
        if (abs(position - (weekdaysRecyclerView.adapter as WeekdaysAdapter)
                        .activeElement) > 80) {
            weekdaysRecyclerView.scrollToPosition(position)
            weekdaysRecyclerView.smoothScrollToPosition(position)
        } else {
            weekdaysRecyclerView.smoothScrollToPosition(position)
        }
        (weekdaysRecyclerView.adapter as WeekdaysAdapter).selectItem(position)
    }

    override fun onClick(view: View) {
        mViewModel.currentWeekday = when (view.id) {
            R.id.weekdayMonday ->
                ScheduleViewModel.Weekday.MONDAY

            R.id.weekdayTuesday ->
                ScheduleViewModel.Weekday.TUESDAY

            R.id.weekdayWednesday ->
                ScheduleViewModel.Weekday.WEDNESDAY

            R.id.weekdayThursday ->
                ScheduleViewModel.Weekday.THURSDAY

            R.id.weekdayFriday ->
                ScheduleViewModel.Weekday.FRIDAY

            else ->
                ScheduleViewModel.Weekday.SATURDAY
        }
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        return if (isAdded) Utils.convertPixelsToDp(requireActivity(), motionEvent.y) > 118
        else true
    }

    override fun onRequestScheduleData(date: Date) {
        mViewModel.getSchedule(date - 1, date + 1)
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}