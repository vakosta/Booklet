package com.booklet.bookletandroid.presentation.activities.main

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.android.billingclient.api.*
import com.booklet.bookletandroid.R
import com.booklet.bookletandroid.domain.Preferences
import com.booklet.bookletandroid.domain.Utils
import com.booklet.bookletandroid.domain.model.Date
import com.booklet.bookletandroid.domain.model.OnPurchase
import com.booklet.bookletandroid.domain.model.PurchaseUpdate
import com.booklet.bookletandroid.domain.model.RestartActivity
import com.booklet.bookletandroid.domain.services.NotificationsService
import com.booklet.bookletandroid.domain.subscriptionHash
import com.booklet.bookletandroid.presentation.ActivityBaseView
import com.booklet.bookletandroid.presentation.CardBaseView
import com.booklet.bookletandroid.presentation.activities.dark_theme_popup.DarkThemePopupActivity
import com.booklet.bookletandroid.presentation.activities.login.LoginDiaryActivity
import com.booklet.bookletandroid.presentation.customviews.NonSwipeableViewPager
import com.booklet.bookletandroid.presentation.fragments.events.EventFilterFragment
import com.booklet.bookletandroid.presentation.model.event.SelectedPage
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import hirondelle.date4j.DateTime
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

class MainActivity : AppCompatActivity(),
        MainView,
        BottomNavigationView.OnNavigationItemSelectedListener,
        View.OnTouchListener, PurchasesUpdatedListener {
    private lateinit var mPresenter: MainPresenter
    private lateinit var starterIntent: Intent

    private lateinit var mFirebaseAnalytics: FirebaseAnalytics
    private lateinit var mBc: BillingClient

    private lateinit var mPager: NonSwipeableViewPager
    private lateinit var mPagerAdapter: MainPagerAdapter

    private lateinit var mDatePicker: DatePickerDialog

    private var mIsHideMenu = false
    private var mMenu = R.menu.menu_bottom_schedule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        starterIntent = intent
        when (Preferences.getInstance(this).isDarkTheme) {
            false -> setTheme(R.style.AppTheme)
            true -> setTheme(R.style.AppThemeBlack)
        }
        setContentView(R.layout.activity_main)
        mPresenter = MainPresenter(this, this)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        mBc = BillingClient.newBuilder(this)
                .enablePendingPurchases()
                .setListener(this).build()
        mBc.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult?) {
                val prefs = Preferences.getInstance(this@MainActivity)
                val purchases = mBc.queryPurchases(BillingClient.SkuType.SUBS)
                prefs.notificationsSubscription = (purchases.billingResult.responseCode == 0
                        && purchases.purchasesList.isNotEmpty())
            }

            override fun onBillingServiceDisconnected() {
            }
        })

        bottomNavigation.setOnNavigationItemSelectedListener(this)

        val prefs = Preferences.getInstance(this)

        if (prefs.userLogin.isEmpty())
            startActivityForResult(Intent(this, LoginDiaryActivity::class.java),
                    REQUEST_LOGIN)
        else if (!prefs.isShowedDarkThemePopup)
            startActivityForResult(Intent(this, DarkThemePopupActivity::class.java),
                    REQUEST_DARK_THEME)

        mPresenter.init()

        mainPager.setOnTouchListener(this)

        if (prefs.notificationsSubscription && prefs.notificationMain) {
            val i = Intent(this, NotificationsService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(i)
            } else {
                startService(i)
            }
        }

        /*if (intent.getBooleanExtra(EXTRA_IS_FROM_SETTINGS, false))
            bottomNavigation.selectedItemId = R.id.navSettings*/
    }

    override fun onPurchasesUpdated(billingResult: BillingResult?,
                                    purchases: MutableList<Purchase>?) {
        if (billingResult!!.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            val prefs = Preferences.getInstance(this)

            if (!prefs.notificationsSubscription) {
                doAsync {
                    val pid = prefs.userPid
                    val token = prefs.userSecret
                    val hash = "$pid$token".subscriptionHash()
                    prefs.notificationsSubscription = true
                    uiThread {
                        EventBus.getDefault().post(PurchaseUpdate())

                        alert(getString(R.string.gratitude),
                                "Спасибо! :)") {
                            positiveButton("Понял, хочу попробовать!") {}
                        }.show()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_LOGIN -> {
                when (resultCode) {
                    RESULT_OK -> mPresenter.initPager()
                    RESULT_BACK_PRESSED -> finish()
                }
            }
            REQUEST_DARK_THEME -> {
                when (resultCode) {
                    RESULT_SET_DARK_THEME ->
                        restartActivity(RestartActivity(false))
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe
    fun onPurchase(onPurchase: OnPurchase) {
        if (!Preferences.getInstance(this).notificationsSubscription) {
            val params = BillingFlowParams.newBuilder().setSkuDetails(SkuDetails("{\"skuDetailsToken\":\"AEuhp4K0XflXJUdybwOSLjEIyUYBaHkNVL1zCMcCUEgV-vMq64Us0Acq9FaGuNBwkJYT\",\"productId\":\"booklet_plus\",\"type\":\"subs\",\"price\":\"249,00 \u20BD\",\"price_amount_micros\":249000000,\"price_currency_code\":\"RUB\",\"subscriptionPeriod\":\"P1Y\",\"title\":\"Booklet Plus (Дневник МЭШ)\",\"description\":\"- Уведомления об оценках\"}"))
                    .build()
            mBc.launchBillingFlow(this, params)
        }
    }

    override fun initPager() {
        mPager = findViewById(R.id.mainPager)
        mPagerAdapter = MainPagerAdapter(supportFragmentManager)
        mPager.adapter = mPagerAdapter
        mPager.offscreenPageLimit = 5
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navSchedule -> {
                setFragment(2)
                EventBus.getDefault().post(SelectedPage(SelectedPage.Page.SCHEDULE))
            }
            R.id.navMarks -> setFragment(1)
            R.id.navAccount -> setFragment(4)
            R.id.navGamefication -> setFragment(0)
            R.id.navEvents -> setFragment(5)
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(mMenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuItemCalendar -> {
                openDatePicker()
                true
            }
            R.id.menuItemFilter -> {
                val bottomNavDrawerFragment = EventFilterFragment()
                bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return Utils.convertPixelsToDp(this, v.y) > 56
    }

    @Subscribe
    fun onNewDate(date: Date) {
        mPresenter.mSelectedDate = date
        setToolbarText(date.monthText)
    }

    @Subscribe
    fun restartActivity(restartActivity: RestartActivity) {
        finish()
        starterIntent.putExtra(EXTRA_IS_FROM_SETTINGS, restartActivity.isFromSettings)
        startActivity(starterIntent)
    }

    override fun initToolbar() {
        //setSupportActionBar(bottomNavigation)
    }

    override fun initCalendar() {
        val dateAndTime = Calendar.getInstance()
        mDatePicker = DatePickerDialog(this@MainActivity,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    EventBus.getDefault().post(DateTime.forDateOnly(year,
                            month + 1,
                            dayOfMonth))
                },
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
    }

    override fun setToolbarText(text: String) {
        toolbar.title = text
    }

    override fun setFragment(position: Int) {
        mPager.setCurrentItem(position, false)
    }

    override fun openDatePicker() {
        mDatePicker.show()
    }

    @Subscribe
    override fun onScroll(position: CardBaseView.ScrollPosition) {
        super.onScroll(position)
    }

    override fun showToolbar() {
        if (!mPresenter.isToolbarShowing) {
            toolbar.animate()
                    .alpha(1F)
                    .duration = ActivityBaseView.DURATION
            mPresenter.isToolbarShowing = true
        }
    }

    override fun hideToolbar() {
        if (mPresenter.isToolbarShowing) {
            toolbar.animate()
                    .alpha(0F)
                    .duration = ActivityBaseView.DURATION
            mPresenter.isToolbarShowing = false
        }
    }

    override fun showMenu() {
        if (!mIsHideMenu) {
            mIsHideMenu = true
            invalidateOptionsMenu()
        }
    }

    override fun hideMenu() {
        if (mIsHideMenu) {
            mIsHideMenu = false
            invalidateOptionsMenu()
        }
    }

    override fun setCalendarMenu() {
        if (mMenu != R.menu.menu_bottom_schedule) {
            mMenu = R.menu.menu_bottom_schedule
            invalidateOptionsMenu()
        }
    }

    override fun setContentBelowToolbar(below: Boolean) {
        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        if (below) {
            //params.be(RelativeLayout.BELOW, R.id.toolbar)
        } else {
            //params.removeRule(RelativeLayout.BELOW)
        }
        mPager.layoutParams = params
    }

    override fun setToolbarIsGone(gone: Boolean) {
        if (gone)
            toolbar.visibility = View.GONE
        else
            toolbar.visibility = View.VISIBLE
    }

    companion object {
        private const val REQUEST_LOGIN = 1
        private const val REQUEST_DARK_THEME = 2

        const val RESULT_OK = 10
        const val RESULT_BACK_PRESSED = 11
        const val RESULT_SET_DARK_THEME = 12
        const val RESULT_CANCEL = 13

        private const val EXTRA_IS_FROM_SETTINGS = "IS_FROM_SETTINGS"
    }
}