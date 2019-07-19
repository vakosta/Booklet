package me.annenkov.julistaandroid.presentation.customviews

import android.graphics.Rect
import android.view.View

class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect,
                                view: View, parent: androidx.recyclerview.widget.RecyclerView,
                                state: androidx.recyclerview.widget.RecyclerView.State) {
        outRect.bottom = verticalSpaceHeight
    }
}