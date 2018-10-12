package me.annenkov.julistaandroid.presentation.fragments.markscard

import me.annenkov.julistaandroid.presentation.model.Progress
import me.annenkov.julistaandroid.presentation.model.Result

interface MarksCardView {
    fun initRecyclerView(progresses: List<Progress>)
    fun initRecyclerViewResults(results: List<Result>)
}