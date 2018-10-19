package me.annenkov.julistaandroid.presentation.fragments.markscard

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_item_pager_marks.*
import me.annenkov.julistaandroid.R
import me.annenkov.julistaandroid.domain.Utils
import me.annenkov.julistaandroid.domain.px
import me.annenkov.julistaandroid.presentation.customviews.VerticalSpaceItemDecoration
import me.annenkov.julistaandroid.presentation.model.Progress
import me.annenkov.julistaandroid.presentation.model.Result

class MarksCardFragment : Fragment(), MarksCardView {
    private lateinit var mPresenter: MarksCardPresenter
    private lateinit var mContext: Context

    private lateinit var mHeader: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = MarksCardPresenter(this)
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

        when (arguments!!.getString(ARGUMENT_TYPE, "")) {
            TYPE_MARKS -> {
                val listType = object :
                        TypeToken<ArrayList<me.annenkov.julistaandroid.presentation.model.Progress>>() {}
                        .type
                val progresses = Gson().fromJson<ArrayList<me.annenkov.julistaandroid.presentation.model
                .Progress>>(arguments!!.getString(ARGUMENT_PROGRESS), listType)
                mPresenter.init(progresses)
            }
            else -> {
                val listType = object : TypeToken<ArrayList<Result>>() {}.type
                val results = Gson().fromJson<ArrayList<Result>>(
                        arguments!!.getString(ARGUMENT_PROGRESS), listType)
                mPresenter.initResults(results)
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context!!
    }

    override fun initRecyclerView(progresses: List<Progress>) {
        val layoutManager = LinearLayoutManager(activity!!)

        marksRecyclerView.layoutManager = layoutManager
        marksRecyclerView.setHasFixedSize(true)
        marksRecyclerView.adapter = SubjectListAdapter(progresses)
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

        fun newInstance(progresses: List<me.annenkov.julistaandroid.presentation.model.Progress>):
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