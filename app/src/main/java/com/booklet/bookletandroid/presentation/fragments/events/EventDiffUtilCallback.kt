package com.booklet.bookletandroid.presentation.fragments.events

import androidx.recyclerview.widget.DiffUtil
import com.booklet.bookletandroid.presentation.model.Event

class EventDiffUtilCallback(
        private val oldList: List<Event>,
        private val newList: List<Event>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.type == newItem.type
                && oldItem.text == newItem.text
                && oldItem.date == newItem.date
    }
}