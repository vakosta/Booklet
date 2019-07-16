package me.annenkov.julistaandroid.presentation.fragments.marks

import me.annenkov.julistaandroid.data.model.booklet.marks.Subject

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