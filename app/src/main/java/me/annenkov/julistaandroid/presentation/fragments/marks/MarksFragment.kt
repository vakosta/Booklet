package me.annenkov.julistaandroid.presentation.fragments.marks

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_marks.*
import me.annenkov.julistaandroid.R
import me.annenkov.julistaandroid.data.model.booklet.marks.Subject
import me.annenkov.julistaandroid.databinding.FragmentMarksBinding
import me.annenkov.julistaandroid.domain.Preferences
import me.annenkov.julistaandroid.domain.Utils
import me.annenkov.julistaandroid.domain.model.Refresh
import me.annenkov.julistaandroid.presentation.ViewPagerFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.yesButton
import kotlin.math.max

class MarksFragment : ViewPagerFragment(), MarksView, View.OnClickListener, View.OnTouchListener {
    private lateinit var binding: FragmentMarksBinding
    lateinit var prefs: Preferences

    private lateinit var mMarksTab: TabLayout
    private lateinit var mPager: ViewPager
    private lateinit var mPagerAdapter: MarksPagerAdapter

    private lateinit var mRefresher: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_marks, container, false)
        binding.viewModel = ViewModelProviders.of(this).get(MarksViewModel::class.java)
        binding.executePendingBindings()
        val view = binding.root

        mPager = view.find(R.id.marksListPager)
        mMarksTab = view.find(R.id.marksTabs)
        mRefresher = view.find(R.id.marksRefresher)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        prefs = Preferences.getInstance(activity!!)
        mRefresher.setColorSchemeResources(R.color.colorAccent)
        mRefresher.onRefresh {
            fetchData()
        }

        binding.viewModel!!.authLiveData.observe(this, Observer {
            binding.viewModel!!.status.set(MarksViewModel.Status.LOADED)
            Log.d("Login", "Auth data received")
            val auth = it?.body()
            when {
                auth == null ->
                    onNetworkProblems()
                !it.isSuccessful ->
                    onUnknownError()
                else -> {
                    initPager(it.body()!!.data!!)
                    showContent()
                    stopRefreshing()
                }
            }
        })
    }

    override fun fetchData() {
        binding.viewModel!!.status.set(MarksViewModel.Status.LOADING)
        binding.viewModel!!.getMarks(
                prefs.userPid!!,
                prefs.userSecret!!
        )
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
        }
    }

    override fun onTouch(p0: View, p1: MotionEvent): Boolean {
        return Utils.convertPixelsToDp(activity!!, p1.y) > 62
    }

    override fun initPager(progresses: List<Subject>) {
        mPagerAdapter = MarksPagerAdapter(fragmentManager ?: return, progresses)
        mPager.adapter = mPagerAdapter
        mPager.offscreenPageLimit = 2
        mPager.currentItem = max(mPagerAdapter.count - 2, 0)

        mPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
                mRefresher.isEnabled = state == ViewPager.SCROLL_STATE_IDLE
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
        })

        marksTabs.setupWithViewPager(mPager)
    }

    override fun showContent() {
        marksListPager.animate()
                .alpha(1F)
    }

    override fun showProgressIndicator() {
        marksProgress.visibility = View.VISIBLE
    }

    override fun hideProgressIndicator() {
        marksProgress.visibility = View.GONE
    }

    override fun showResponseError() {
        responseErrorView.visibility = View.VISIBLE
    }

    override fun showNetworkError() {
        networkErrorView.visibility = View.VISIBLE
    }

    override fun hideResponseError() {
        responseErrorView.visibility = View.GONE
    }

    override fun hideNetworkError() {
        networkErrorView.visibility = View.GONE
    }

    override fun stopRefreshing() {
        mRefresher.isRefreshing = false
    }

    private fun onNetworkProblems() {
        binding.viewModel!!.status.set(MarksViewModel.Status.ERROR_NETWORK)
        alert("Проверьте подключение к интернету.") { yesButton {} }
                .show()
    }

    private fun onUnknownError() {
        binding.viewModel!!.status.set(MarksViewModel.Status.ERROR_UNKNOWN)
        alert("Произошла ошибка. Попробуйте ещё раз.") { yesButton {} }
                .show()
    }
}