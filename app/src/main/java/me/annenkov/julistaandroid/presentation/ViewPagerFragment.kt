package me.annenkov.julistaandroid.presentation

import android.os.Bundle

abstract class ViewPagerFragment : SafeFragment() {
    private var mIsStarted = false
    private var mIsVisible = false
    private var mHasInflated = false

    fun refresh() {
        mHasInflated = false
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mIsStarted = true

        if (mIsStarted && mIsVisible && !mHasInflated) {
            mHasInflated = true
            fetchData()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mIsVisible = isVisibleToUser

        if (mIsStarted && mIsVisible && !mHasInflated) {
            mHasInflated = true
            fetchData()
        }
    }

    abstract fun fetchData()
}