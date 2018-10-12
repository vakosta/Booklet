package me.annenkov.julistaandroid.presentation.fragments.markscard

import me.annenkov.julistaandroid.presentation.model.Progress
import me.annenkov.julistaandroid.presentation.model.Result

class MarksCardPresenter(
        private val view: MarksCardView
) {
    fun init(progresses: ArrayList<Progress>) {
        view.initRecyclerView(progresses)
    }

    fun initResults(results: ArrayList<Result>) {
        view.initRecyclerViewResults(results)
    }
}