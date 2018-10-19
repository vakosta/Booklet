package me.annenkov.julistaandroid.presentation.fragments.account

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_account.*
import me.annenkov.julistaandroid.R
import me.annenkov.julistaandroid.domain.Preferences
import me.annenkov.julistaandroid.presentation.ViewPagerFragment
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.selector

class AccountFragment : ViewPagerFragment(), AccountView {
    private lateinit var mPresenter: AccountPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = AccountPresenter(this, activity!!)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        accountRefresher.setColorSchemeResources(R.color.colorAccent)
        accountRefresher.onRefresh {
            mPresenter.init()
        }
        changeProfileButton.setOnClickListener { _ ->
            val prefs = Preferences.getInstance(activity!!)
            val names = prefs.userStudentProfiles.map { it.name.toString() }
            val ids = prefs.userStudentProfiles.map { it.studentProfileId }
            if (names.isNotEmpty()) {
                selector("Выберите аккаунт ребёнка:", names) { _, i ->
                    prefs.userStudentProfileId = ids[i].toString()
                }
            } else {
                alert("Школьные профили ещё не подгрузились. " +
                        "Зайдите сюда через несколько часов, и они появятся. :)") {
                    positiveButton("Хорошо, жду") {}
                }.show()
            }
        }
    }

    override fun fetchData() {
        mPresenter.init()
    }

    override fun setBackgroundBlueColor() {
        parentLayout.backgroundColor = ContextCompat.getColor(activity!!, R.color.colorBackground)
    }

    override fun setBackgroundGreyColor() {
        parentLayout.backgroundColor = Color.WHITE
    }

    override fun setStudentName(name: String) {
        accountStudentName.text = name
    }

    override fun setStudentGrade(grade: String) {
        accountStudentGrade.text = grade
    }

    override fun setLevel(level: Int, intermediatePoints: Int) {
        levelNumber.text = level.toString()
    }

    override fun setXpLeft(xp: Int) {
        xpLeft.text = "Ещё $xp опыта"
    }

    override fun setXpRatio(xpCount: Int, xpLeft: Int) {
        val countParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        countParams.weight = xpLeft.toFloat()

        val leftParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        leftParams.weight = xpCount.toFloat()

        graphXpCount.layoutParams = countParams
        graphXpLeft.layoutParams = leftParams
    }

    override fun setStatusText(text: String) {
        statusContent.text = text
    }

    override fun setTop(layout: LinearLayout) {
        topBlock.removeAllViews()
        topBlock.addView(layout)
    }

    override fun setNews(layout: LinearLayout) {
        newsBlock.removeAllViews()
        newsBlock.addView(layout)
    }

    override fun showTop() {
        topBlock.visibility = View.VISIBLE
        topBlock.animate()
                .alpha(1F)
    }

    override fun showNews() {
        newsBlock.visibility = View.VISIBLE
        newsBlock.animate()
                .alpha(1F)
    }

    override fun stopRefreshing() {
        accountRefresher.isRefreshing = false
    }
}