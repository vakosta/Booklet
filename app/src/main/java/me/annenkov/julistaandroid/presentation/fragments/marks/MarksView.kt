package me.annenkov.julistaandroid.presentation.fragments.marks

import me.annenkov.julistaandroid.domain.model.mos.ProgressResponse

interface MarksView {
    fun initPager(progresses: List<ProgressResponse>)

    fun showContent()

    fun showProgressIndicator()
    fun hideProgressIndicator()

    fun showResponseError()
    fun showNetworkError()

    fun hideResponseError()
    fun hideNetworkError()

    fun stopRefreshing()
}