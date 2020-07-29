package com.booklet.bookletandroid.presentation.model.event

data class SelectedPage(val page: Page) {
    enum class Page {
        SCHEDULE,
        MARKS,
        GAMEFICATION
    }
}