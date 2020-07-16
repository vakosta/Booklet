package com.booklet.bookletandroid.presentation.fragments.markscard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.booklet.bookletandroid.R
import com.booklet.bookletandroid.domain.px
import com.booklet.bookletandroid.presentation.customviews.VerticalSpaceItemDecoration
import com.booklet.bookletandroid.presentation.model.event.Progress
import com.booklet.bookletandroid.presentation.model.event.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_item_pager_marks.*

class MarksCardFragment : Fragment(), MarksCardView {
    private lateinit var mContext: Context

    private lateinit var mHeader: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_item_pager_marks,
                container,
                false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        when (requireArguments().getString(ARGUMENT_TYPE, "")) {
            TYPE_MARKS -> {
                val listType = object :
                        TypeToken<ArrayList<Progress>>() {}
                        .type
                val progresses = Gson().fromJson<ArrayList<Progress>>(requireArguments()
                        .getString(ARGUMENT_PROGRESS), listType)
                initRecyclerView(progresses)
            }
            else -> {
                val listType = object : TypeToken<ArrayList<Result>>() {}.type
                val results = Gson().fromJson<ArrayList<Result>>(
                        requireArguments().getString(ARGUMENT_PROGRESS), listType)
                initRecyclerViewResults(results)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun initRecyclerView(progresses: List<Progress>) {
        val layoutManager = LinearLayoutManager(requireActivity())

        marksRecyclerView.layoutManager = layoutManager
        marksRecyclerView.setHasFixedSize(true)
        marksRecyclerView.adapter = SubjectListAdapter(requireActivity(), progresses)
        marksRecyclerView.setItemViewCacheSize(30)
        marksRecyclerView.isDrawingCacheEnabled = true
        marksRecyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        marksRecyclerView
                .addItemDecoration(
                        VerticalSpaceItemDecoration(12.px)
                )
    }

    override fun initRecyclerViewResults(results: List<Result>) {
        val layoutManager = LinearLayoutManager(activity)

        marksRecyclerView.layoutManager = layoutManager
        marksRecyclerView.setHasFixedSize(true)
        marksRecyclerView.adapter = ResultListAdapter(results)
        marksRecyclerView.setItemViewCacheSize(30)
        marksRecyclerView.isDrawingCacheEnabled = true
        marksRecyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        marksRecyclerView
                .addItemDecoration(
                        VerticalSpaceItemDecoration(12.px)
                )
    }

    companion object {
        const val TYPE_MARKS = "type_marks"
        const val TYPE_RESULTS = "type_results"

        const val ARGUMENT_TYPE = "arg_type"
        const val ARGUMENT_PROGRESS = "arg_progress"

        fun newInstance(progresses: List<Progress>):
                MarksCardFragment {
            val fragment = MarksCardFragment()
            val arguments = Bundle()
            arguments.putString(ARGUMENT_TYPE, TYPE_MARKS)
            arguments.putString(ARGUMENT_PROGRESS, Gson().toJson(progresses))
            fragment.arguments = arguments
            return fragment
        }

        fun newInstanceResults(results: List<Result>): MarksCardFragment {
            val fragment = MarksCardFragment()
            val arguments = Bundle()
            arguments.putString(ARGUMENT_TYPE, TYPE_RESULTS)
            arguments.putString(ARGUMENT_PROGRESS, Gson().toJson(results))
            fragment.arguments = arguments
            return fragment
        }
    }
}