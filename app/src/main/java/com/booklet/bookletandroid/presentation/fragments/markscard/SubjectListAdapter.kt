package com.booklet.bookletandroid.presentation.fragments.markscard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.booklet.bookletandroid.R
import com.booklet.bookletandroid.domain.Preferences
import com.booklet.bookletandroid.presentation.model.Progress

class SubjectListAdapter(
        private val mContext: Context,
        private val mProgresses: List<Progress>
) : androidx.recyclerview.widget.RecyclerView.Adapter<SubjectHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectHolder =
            SubjectHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_mark_subject,
                            parent,
                            false), mContext)

    override fun onBindViewHolder(holder: SubjectHolder, position: Int) {
        holder.bind(mProgresses[position], Preferences.getInstance(mContext).markPurpose)
    }

    override fun getItemCount(): Int = mProgresses.size
}