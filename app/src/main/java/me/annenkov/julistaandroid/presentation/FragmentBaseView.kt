package me.annenkov.julistaandroid.presentation

interface FragmentBaseView {
    fun onScroll(position: CardBaseView.ScrollPosition) {
        when (position) {
            CardBaseView.ScrollPosition.TOP -> showPeriodBar()
            else -> hidePeriodBar()
        }
    }

    fun showPeriodBar()
    fun hidePeriodBar()

    companion object {
        const val DURATION = 90L
    }
}