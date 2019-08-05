package com.booklet.bookletandroid.presentation.fragments.markscard

import com.booklet.bookletandroid.presentation.model.Progress
import com.booklet.bookletandroid.presentation.model.Result

interface MarksCardView {
    fun initRecyclerView(progresses: List<Progress>)
    fun initRecyclerViewResults(results: List<Result>)
}