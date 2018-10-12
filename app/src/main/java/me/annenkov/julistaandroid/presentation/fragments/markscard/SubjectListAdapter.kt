package me.annenkov.julistaandroid.presentation.fragments.markscard

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import me.annenkov.julistaandroid.R
import me.annenkov.julistaandroid.presentation.model.Progress

class SubjectListAdapter(
        private val mProgresses: List<Progress>
) : RecyclerView.Adapter<SubjectHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectHolder =
            SubjectHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_mark_subject,
                            parent,
                            false))

    override fun onBindViewHolder(holder: SubjectHolder, position: Int) {
        holder.bind(mProgresses[position])
    }

    override fun getItemCount(): Int = mProgresses.size
}