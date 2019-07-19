package me.annenkov.julistaandroid.presentation.fragments.account

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import me.annenkov.julistaandroid.R
import me.annenkov.julistaandroid.data.model.julista.account.Account
import me.annenkov.julistaandroid.data.model.julista.account.Level
import me.annenkov.julistaandroid.data.model.julista.account.News
import me.annenkov.julistaandroid.data.model.julista.account.Top
import me.annenkov.julistaandroid.domain.ApiHelper
import me.annenkov.julistaandroid.domain.DateHelper
import me.annenkov.julistaandroid.domain.attribute
import me.annenkov.julistaandroid.domain.px
import me.annenkov.julistaandroid.presentation.InitContentPresenter
import org.jetbrains.anko.textColor

class AccountPresenter(private val view: AccountView,
                       private val mContext: Context) : InitContentPresenter(mContext) {
    fun init() {
        initContent()
    }

    private fun initLevel(level: Level) {
        view.setLevel(level.level!!, level.xpCount!!)
        view.setXpLeft(level.xpLeft!!)
        view.setXpRatio(level.percent!! + 1, 100 - level.percent!!)
    }

    private fun initStatus(scaleOfSuccess: Int) {
        val status = when {
            scaleOfSuccess == 100 -> "Безупречная"
            scaleOfSuccess >= 85 -> "Очень высокая"
            scaleOfSuccess >= 65 -> "Высокая"
            scaleOfSuccess >= 50 -> "Средняя"
            scaleOfSuccess >= 35 -> "Ниже среднего"
            else -> "Низкая"
        }
        view.setStatusText(status)
    }

    private fun initTop(top: Top) {
        val topLayout = LinearLayout(mContext)
        topLayout.orientation = LinearLayout.VERTICAL

        val headerParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        headerParams.bottomMargin = 6.px
        headerParams.topMargin = 6.px
        headerParams.marginStart = 28.px
        headerParams.marginEnd = 28.px
        val header = TextView(mContext)
        header.text = "Топ класса"
        header.textColor = mContext.attribute(R.attr.colorTextTop).data
        header.layoutParams = headerParams
        topLayout.addView(header)

        val inflater = LayoutInflater.from(mContext)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        params.bottomMargin = 8.px
        params.marginStart = 12.px
        params.marginEnd = 12.px
        top.topList!!.forEachIndexed { index, element ->
            val view = inflater.inflate(R.layout.item_top, null, false)
            view.layoutParams = params
            view.findViewById<TextView>(R.id.topItemIndex).text = (index + 1).toString()
            view.findViewById<TextView>(R.id.topItemStudentName).text = element.name
            view.findViewById<TextView>(R.id.topItemLevel).text = element.level.toString()
            view.findViewById<TextView>(R.id.topItemLevel).background = when (index) {
                0 -> ContextCompat.getDrawable(mContext, R.drawable.background_level_top_1)
                1 -> ContextCompat.getDrawable(mContext, R.drawable.background_level_top_2)
                else -> ContextCompat.getDrawable(mContext, R.drawable.background_level_top_3)
            }
            topLayout.addView(view)
        }

        if (top.myPosition!! > 3) {
            val positionParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
            positionParams.bottomMargin = 6.px
            positionParams.marginStart = 28.px
            positionParams.marginEnd = 28.px
            val myPosition = TextView(mContext)
            myPosition.text = "Твоя позиция ${top.myPosition}"
            myPosition.textColor = mContext.attribute(R.attr.colorTextTop).data
            myPosition.layoutParams = positionParams
            topLayout.addView(myPosition)
        }

        if (top.topList!!.isNotEmpty()) {
            view.setTop(topLayout)
            view.showTop()
        }
    }

    private fun initNews(news: List<News>) {
        val newsLayout = LinearLayout(mContext)
        newsLayout.orientation = LinearLayout.VERTICAL

        val headerParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        headerParams.bottomMargin = 6.px
        headerParams.topMargin = 6.px
        headerParams.marginStart = 28.px
        headerParams.marginEnd = 28.px
        val header = TextView(mContext)
        header.text = "Последние события"
        header.textColor = Color.WHITE
        header.layoutParams = headerParams
        newsLayout.addView(header)

        val inflater = LayoutInflater.from(mContext)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        params.bottomMargin = 1.px
        params.marginStart = 12.px
        params.marginEnd = 12.px
        news.forEachIndexed { index, element ->
            if (index > 2)
                return@forEachIndexed

            val view = inflater.inflate(R.layout.item_news, null, false)
            view.layoutParams = params
            view.findViewById<TextView>(R.id.itemNewsContent).text = element.content
            view.findViewById<TextView>(R.id.itemNewsDate).text = DateHelper.getTermString(element.date!!)
            view.isClickable = true
            view.isFocusable = true
            view.background = when {
                index == 0 && index == (news.size - 1) -> ContextCompat.getDrawable(mContext,
                        R.drawable.background_item_news_only)
                index == 0 -> ContextCompat.getDrawable(mContext,
                        R.drawable.background_item_news_first)
                index == (news.size - 1) -> ContextCompat.getDrawable(mContext,
                        R.drawable.background_item_news_last)
                else -> ContextCompat.getDrawable(mContext,
                        R.drawable.background_item_news)
            }

            newsLayout.addView(view)
        }

        /*if (news.isNotEmpty()) {
            val view = inflater.inflate(R.layout.button_news_more,
                    null,
                    false)
            buildNewsItem(view as TextView, news.size, true)
            newsLayout.addView(view)
        }*/

        if (news.isNotEmpty()) {
            view.setNews(newsLayout)
            view.showNews()
            view.setBackgroundBlueColor()
        }
    }

    fun buildNewsItem(view: TextView, index: Int, isLast: Boolean) {
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        params.bottomMargin = 1.px
        params.marginStart = 12.px
        params.marginEnd = 12.px

        view.layoutParams = params
        view.isClickable = true
        view.isFocusable = true
        view.background = when {
            index == 0 -> ContextCompat.getDrawable(mContext, R.drawable.background_item_news_first)
            isLast -> ContextCompat.getDrawable(mContext, R.drawable.background_item_news_last)
            else -> ContextCompat.getDrawable(mContext, R.drawable.background_item_news)
        }
    }

    override fun executeMethod(): Account = ApiHelper.getInstance(mContext)
            .getAccount(prefs.userSecret!!,
                    prefs.userStudentProfileId).execute().body()!!

    override fun onSuccessful(response: Any) {
        val account = (response as Account)

        view.setStudentName(account.studentName ?: "")
        view.setStudentGrade(account.studentGrade ?: "")
        initLevel(account.level!!)
        initStatus(account.scaleOfSuccess!!)
        initTop(account.top!!)
        initNews(account.news!!)
        view.removePadding()

        view.stopRefreshing()
    }

    override fun onFailureResponse() {
        view.stopRefreshing()
    }

    override fun onFailureNetwork() {
        view.stopRefreshing()
    }
}