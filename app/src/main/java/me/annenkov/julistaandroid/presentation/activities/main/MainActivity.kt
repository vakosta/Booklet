package me.annenkov.julistaandroid.presentation.activities.main

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.RelativeLayout
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.TransactionDetails
import com.crashlytics.android.Crashlytics
import com.google.firebase.analytics.FirebaseAnalytics
import hirondelle.date4j.DateTime
import kotlinx.android.synthetic.main.activity_main.*
import me.annenkov.julistaandroid.R
import me.annenkov.julistaandroid.domain.*
import me.annenkov.julistaandroid.domain.model.Date
import me.annenkov.julistaandroid.domain.model.OnPurchase
import me.annenkov.julistaandroid.domain.model.PurchaseUpdate
import me.annenkov.julistaandroid.domain.model.RestartActivity
import me.annenkov.julistaandroid.domain.services.NotificationsService
import me.annenkov.julistaandroid.presentation.ActivityBaseView
import me.annenkov.julistaandroid.presentation.CardBaseView
import me.annenkov.julistaandroid.presentation.activities.dark_theme_popup.DarkThemePopupActivity
import me.annenkov.julistaandroid.presentation.activities.login.LoginActivity
import me.annenkov.julistaandroid.presentation.customviews.NonSwipeableViewPager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

class MainActivity : AppCompatActivity(),
        MainView,
        BottomNavigationView.OnNavigationItemSelectedListener,
        View.OnTouchListener,
        BillingProcessor.IBillingHandler {
    private lateinit var mPresenter: MainPresenter
    private lateinit var starterIntent: Intent

    private lateinit var mFirebaseAnalytics: FirebaseAnalytics
    private lateinit var mBp: BillingProcessor

    private lateinit var mPager: NonSwipeableViewPager
    private lateinit var mPagerAdapter: MainPagerAdapter

    private lateinit var mDatePicker: DatePickerDialog

    private var mIsHideMenu = false
    private var mMenu = R.menu.menu_calendar

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
        mBp = BillingProcessor(this, getString(R.string.license_key), this)
        mBp.initialize()

        val prefs = Preferences.getInstance(this)

        if (prefs.userLogin.isEmpty())
            startActivityForResult(Intent(this, LoginActivity::class.java),
                    REQUEST_LOGIN)
        else if (!prefs.isShowedDarkThemePopup)
            startActivityForResult(Intent(this, DarkThemePopupActivity::class.java),
                    REQUEST_DARK_THEME)

        mPresenter.init()

        bottomNavigation.selectedItemId = R.id.navSchedule

        bottomNavigation.setOnNavigationItemSelectedListener(this)
        mainPager.setOnTouchListener(this)

        if (prefs.notificationsSubscription && prefs.notificationMain) {
            val i = Intent(this, NotificationsService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(i)
            } else {
                startService(i)
            }
        }

        if (intent.getBooleanExtra(EXTRA_IS_FROM_SETTINGS, false))
            bottomNavigation.selectedItemId = R.id.navSettings
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!mBp.handleActivityResult(requestCode, resultCode, data))
            when (requestCode) {
                REQUEST_LOGIN -> {
                    when (resultCode) {
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

        doAsync {
            val prefs = Preferences.getInstance(this@MainActivity)

            val pid = prefs.userPid
            val token = prefs.userToken
            val hash = "$pid$token".subscriptionHash()
            val result = ApiHelper.getInstance(this@MainActivity)
                    .checkNotificationsSubscription(pid)
                    .execute().body()?.result ?: false
            val isPurchasedSuccessfully = mBp
                    .getPurchaseTransactionDetails("notifications_20182019")
                    ?.purchaseInfo
                    ?.purchaseData
                    ?.purchaseState?.name == "PurchasedSuccessfully"

            if (!result && isPurchasedSuccessfully) {
                prefs.notificationsSubscription = true
                try {
                    ApiHelper.getInstance(this@MainActivity)
                            .setNotificationsSubscription(pid, token, hash).execute()
                } catch (e: Exception) {
                    Crashlytics.logException(e)
                }
                uiThread { EventBus.getDefault().post(PurchaseUpdate()) }
            } else if (result && !prefs.notificationsSubscription) {
                prefs.notificationsSubscription = true
                uiThread { EventBus.getDefault().post(PurchaseUpdate()) }
            } else if (!result && !isPurchasedSuccessfully && prefs.notificationsSubscription) {
                prefs.notificationsSubscription = false
                uiThread { EventBus.getDefault().post(PurchaseUpdate()) }
            }
        }
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    override fun onDestroy() {
        mBp.release()
        super.onDestroy()
    }

    override fun onBillingInitialized() {
    }

    override fun onPurchaseHistoryRestored() {
    }

    override fun onProductPurchased(productId: String, details: TransactionDetails?) {
        val prefs = Preferences.getInstance(this)

        if (!prefs.notificationsSubscription) {
            doAsync {
                val pid = prefs.userPid
                val token = prefs.userToken
                val hash = "$pid$token".subscriptionHash()
                prefs.notificationsSubscription = true
                uiThread {
                    EventBus.getDefault().post(PurchaseUpdate())

                    alert(getString(R.string.gratitude),
                            "Спасибо! :)") {
                        positiveButton("Понял, хочу попробовать!") {}
                    }.show()
                }

                ApiHelper.getInstance(this@MainActivity)
                        .setNotificationsSubscription(pid, token, hash).execute()
            }
        }
    }

    override fun onBillingError(errorCode: Int, error: Throwable?) {
    }

    @Subscribe
    fun onPurchase(onPurchase: OnPurchase) {
        if (!Preferences.getInstance(this).notificationsSubscription)
            mBp.purchase(this, "notifications_20182019")
    }

    override fun initPager() {
        mPager = findViewById(R.id.mainPager)
        mPagerAdapter = MainPagerAdapter(supportFragmentManager)
        mPager.adapter = mPagerAdapter
        mPager.offscreenPageLimit = 5
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        mPresenter.setFragmentByNavigationItem(item.itemId)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(mMenu, menu)
        for (i in 0 until menu.size())
            menu.getItem(i).isVisible = mIsHideMenu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuItemCalendar -> {
                openDatePicker()
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
        setToolbarText(DateHelper.getMonthNameByNumber(date.month))
    }

    @Subscribe
    fun restartActivity(restartActivity: RestartActivity) {
        finish()
        starterIntent.putExtra(EXTRA_IS_FROM_SETTINGS, restartActivity.isFromSettings)
        startActivity(starterIntent)
    }

    override fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    override fun initCalendar() {
        val dateAndTime = Calendar.getInstance()
        mDatePicker = DatePickerDialog(this@MainActivity,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    EventBus.getDefault().post(DateTime.forDateOnly(year, month + 1, dayOfMonth))
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
        if (mMenu != R.menu.menu_calendar) {
            mMenu = R.menu.menu_calendar
            invalidateOptionsMenu()
        }
    }

    override fun setContentBelowToolbar(below: Boolean) {
        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        if (below) {
            params.addRule(RelativeLayout.BELOW, R.id.toolbar)
        } else {
            params.removeRule(RelativeLayout.BELOW)
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