package me.annenkov.julistaandroid.presentation.fragments.schedulecard

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import me.annenkov.julistaandroid.R
import me.annenkov.julistaandroid.domain.model.Date
import me.annenkov.julistaandroid.domain.px
import me.annenkov.julistaandroid.presentation.CardBaseView
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onRefresh

class ScheduleCardFragment : Fragment(), ScheduleCardView {
    private lateinit var mPresenter: ScheduleCardPresenter
    private lateinit var mContext: Context

    private lateinit var mHeader: TextView
    private lateinit var mScheduleLayout: LinearLayout

    private lateinit var mRefresher: SwipeRefreshLayout

    private var mHasInflated = false
    private var mIsVisibleToUser = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = ScheduleCardPresenter(this, activity!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_card, container, false)

        val scheduleListScrollView: ScrollView = view.find(R.id.cardListScrollView)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scheduleListScrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
                mPresenter.scrollY = scrollY
                onScroll(scrollY)
            }
        }

        mScheduleLayout = view.find(R.id.cardList)
        val params = mScheduleLayout.layoutParams as LinearLayout.LayoutParams
        params.setMargins(0, 118.px, 0, 0)
        mScheduleLayout.layoutParams = params

        mHeader = view.find(R.id.cardHeader)

        mRefresher = view.find(R.id.cardRefresher)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter.init(arguments!!.getInt(ARGUMENT_POSITION),
                arguments!!.getString(ARGUMENT_DATE))
        mRefresher.setColorSchemeResources(R.color.colorAccent)
        mRefresher.onRefresh {
            mPresenter.init(arguments!!.getInt(ARGUMENT_POSITION),
                    arguments!!.getString(ARGUMENT_DATE))
        }
        mHasInflated = true

        if (mIsVisibleToUser) {
            onScroll(mPresenter.scrollY)

            val date = mPresenter.mDate.split(".")
            EventBus.getDefault().post(Date(date[0].toInt(), date[1].toInt(), date[2].toInt()))
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mIsVisibleToUser = isVisibleToUser

        if (isVisibleToUser && mHasInflated) {
            onScroll(mPresenter.scrollY)

            val date = mPresenter.mDate.split(".")
            EventBus.getDefault().post(Date(date[0].toInt(), date[1].toInt(), date[2].toInt()))
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context!!
    }

    fun onScroll(scrollY: Int) {
        when {
            scrollY <= 6.px ->
                EventBus.getDefault().post(CardBaseView.ScrollPosition.TOP)
            scrollY <= 72.px ->
                EventBus.getDefault().post(CardBaseView.ScrollPosition.ABOVE_PERIODS)
            else ->
                EventBus.getDefault().post(CardBaseView.ScrollPosition.ABOVE_TOOLBAR)
        }
    }

    override fun setContentLayout(layout: ViewGroup) {
        mScheduleLayout.removeAllViews()

        layout.alpha = 0f
        mScheduleLayout.addView(layout)

        layout.animate()
                .alpha(1F)
                .duration = 100
    }

    override fun setEmptyContentLayout() {
        mHeader.text = "Уроков нет"
    }

    override fun setHeaderText(text: String) {
        mHeader.text = text
    }

    override fun stopRefreshing() {
        mRefresher.isRefreshing = false
    }

    companion object {
        const val ARGUMENT_POSITION = "arg_page_position"
        const val ARGUMENT_DATE = "arg_date"

        fun newInstance(position: Int, date: String): ScheduleCardFragment {
            val fragment = ScheduleCardFragment()
            val arguments = Bundle()
            arguments.putInt(ARGUMENT_POSITION, position)
            arguments.putString(ARGUMENT_DATE, date)
            fragment.arguments = arguments
            return fragment
        }
    }
}