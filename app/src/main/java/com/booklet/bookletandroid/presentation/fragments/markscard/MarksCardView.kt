package com.booklet.bookletandroid.presentation.fragments.markscard

import com.booklet.bookletandroid.presentation.model.event.Progress
import com.booklet.bookletandroid.presentation.model.event.Result

interface MarksCardView {
    fun initRecyclerView(progresses: List<Progress>)
    fun initRecyclerViewResults(results: List<Result>)
}