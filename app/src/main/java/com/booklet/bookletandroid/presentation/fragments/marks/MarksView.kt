package com.booklet.bookletandroid.presentation.fragments.marks

import com.booklet.bookletandroid.data.model.booklet.marks.Subject

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