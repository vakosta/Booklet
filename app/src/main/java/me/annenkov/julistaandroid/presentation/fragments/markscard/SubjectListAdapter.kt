package me.annenkov.julistaandroid.presentation.fragments.markscard

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import me.annenkov.julistaandroid.R
import me.annenkov.julistaandroid.domain.Preferences
import me.annenkov.julistaandroid.presentation.model.Progress

class SubjectListAdapter(
        private val mContext: Context,
        private val mProgresses: List<Progress>
) : RecyclerView.Adapter<SubjectHolder>() {
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