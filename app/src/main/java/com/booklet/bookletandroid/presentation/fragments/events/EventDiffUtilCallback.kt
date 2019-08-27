package com.booklet.bookletandroid.presentation.fragments.events

import androidx.recyclerview.widget.DiffUtil

class EventDiffUtilCallback(
        private val oldList: ArrayList<com.booklet.bookletandroid.data.model.booklet.events.Event>,
        private val newList: List<com.booklet.bookletandroid.data.model.booklet.events.Event>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].date == newList[newItemPosition].date
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.type == newItem.type
                && oldItem.text == newItem.text
                && oldItem.date == newItem.date
    }
}