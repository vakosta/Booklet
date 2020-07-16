package com.booklet.bookletandroid.presentation.model.event

data class Filter(val state: State) {
    enum class State {
        CLOSED
    }
}