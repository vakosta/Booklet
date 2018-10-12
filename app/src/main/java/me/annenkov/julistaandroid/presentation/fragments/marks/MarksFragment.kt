package me.annenkov.julistaandroid.presentation.fragments.marks

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_marks.*
import me.annenkov.julistaandroid.R
import me.annenkov.julistaandroid.domain.Utils
import me.annenkov.julistaandroid.domain.model.mos.ProgressResponse
import me.annenkov.julistaandroid.presentation.ViewPagerFragment
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onRefresh

class MarksFragment : ViewPagerFragment(), MarksView, View.OnClickListener, View.OnTouchListener {
    private lateinit var mPresenter: MarksPresenter

    private lateinit var mPager: ViewPager
    private lateinit var mPagerAdapter: MarksPagerAdapter

    private lateinit var mRefresher: SwipeRefreshLayout

    private lateinit var mMarksTab: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = MarksPresenter(this, activity!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_marks, container, false)
        mPager = view.find(R.id.marksListPager)

        mMarksTab = view.find(R.id.marksTabs)

        mRefresher = view.find(R.id.marksRefresher)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mRefresher.setColorSchemeResources(R.color.colorAccent)
        mRefresher.onRefresh {
            fetchData()
        }
    }

    override fun fetchData() {
        mPresenter.init()
    }

    override fun onClick(v: View) {
        when (v.id) {
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(p0: View, p1: MotionEvent): Boolean {
        return Utils.convertPixelsToDp(activity!!, p1.y) > 62
    }

    override fun initPager(progresses: List<ProgressResponse>) {
        mPresenter.setPosition(0)
        mPagerAdapter = MarksPagerAdapter(fragmentManager ?: return, progresses)
        mPager.adapter = mPagerAdapter
        mPager.offscreenPageLimit = 2

        mPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                mPresenter.setPosition(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
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
}