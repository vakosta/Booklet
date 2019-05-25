package me.annenkov.julistaandroid.presentation.fragments.marks

import android.content.Context
import me.annenkov.julistaandroid.domain.ApiHelper
import me.annenkov.julistaandroid.domain.model.mos.ProgressResponse
import me.annenkov.julistaandroid.presentation.InitContentPresenter

class MarksPresenter(
        private val view: MarksView,
        private val mContext: Context
) : InitContentPresenter(mContext) {
    var mPeriod: Int = 1

    fun init() {
        initContent()
    }

    fun setPosition(index: Int) {
        mPeriod = index
    }

    override fun executeMethod(): List<ProgressResponse> = ApiHelper.getInstance(mContext)
            .getProgress(prefs.userSecret!!,
                    prefs.userPid!!.toInt(),
                    prefs.userStudentProfileId.toInt())

    override fun onSuccessful(response: Any) {
        view.initPager((response as List<ProgressResponse>))
        view.showContent()

        view.stopRefreshing()
    }

    override fun onFailureResponse() {
        view.hideProgressIndicator()
        view.hideNetworkError()
        view.showResponseError()

        view.stopRefreshing()
    }

    override fun onFailureNetwork() {
        view.hideProgressIndicator()
        view.hideResponseError()
        view.showNetworkError()

        view.stopRefreshing()
    }
}