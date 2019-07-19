package me.annenkov.julistaandroid.presentation.fragments.markscard

import android.view.LayoutInflater
import android.view.ViewGroup
import me.annenkov.julistaandroid.R
import me.annenkov.julistaandroid.presentation.model.Result

class ResultListAdapter(
        private val mResults: List<Result>
) : androidx.recyclerview.widget.RecyclerView.Adapter<ResultHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultHolder =
            ResultHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_result,
                            parent,
                            false))

    override fun onBindViewHolder(holder: ResultHolder, position: Int) {
        holder.bind(mResults[position])
    }

    override fun getItemCount(): Int = mResults.size
}