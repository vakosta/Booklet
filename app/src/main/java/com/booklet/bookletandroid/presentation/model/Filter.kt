package com.booklet.bookletandroid.presentation.model

data class Filter(val state: State) {
    enum class State {
        CLOSED
    }
}