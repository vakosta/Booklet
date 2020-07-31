package com.booklet.bookletandroid.presentation.fragments.marks

import com.booklet.bookletandroid.presentation.model.marks.Subject

interface MarksView {
    fun initPager(progresses: List<Subject>)

    fun showContent()

    fun showProgressIndicator()
    fun hideProgressIndicator()

    fun showResponseError()
    fun showNetworkError()

    fun hideResponseError()
    fun hideNetworkError()

    fun stopRefreshing()
}