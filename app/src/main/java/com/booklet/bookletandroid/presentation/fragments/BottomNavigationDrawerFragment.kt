package com.booklet.bookletandroid.presentation.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.booklet.bookletandroid.R
import com.booklet.bookletandroid.presentation.model.NavigationDrawerItem
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.internal.NavigationMenuView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_navigation_drawer.*
import org.greenrobot.eventbus.EventBus

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        navigation_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navSchedule ->
                    EventBus.getDefault().post(NavigationDrawerItem(ID_SCHEDULE))
                R.id.navMarks ->
                    EventBus.getDefault().post(NavigationDrawerItem(ID_MARKS))
                R.id.navAccount ->
                    EventBus.getDefault().post(NavigationDrawerItem(ID_GAMEFICATION))
                R.id.navSettings ->
                    EventBus.getDefault().post(NavigationDrawerItem(ID_SETTINGS))
                R.id.navEvents ->
                    EventBus.getDefault().post(NavigationDrawerItem(ID_EVENTS))
            }
            dismiss()
            true
        }

        close_imageview.setOnClickListener {
            dismiss()
        }

        disableNavigationViewScrollbars(navigation_view)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog

            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
            bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    if (slideOffset > 0.5) {
                        close_imageview.visibility = View.VISIBLE
                    } else {
                        close_imageview.visibility = View.GONE
                    }
                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_HIDDEN -> dismiss()
                        else -> close_imageview.visibility = View.GONE
                    }
                }
            })
        }

        return dialog
    }

    private fun disableNavigationViewScrollbars(navigationView: NavigationView?) {
        val navigationMenuView = navigationView?.getChildAt(0) as NavigationMenuView
        navigationMenuView.isVerticalScrollBarEnabled = false
    }

    companion object {
        const val ID_SCHEDULE = 1
        const val ID_MARKS = 2
        const val ID_GAMEFICATION = 3
        const val ID_EVENTS = 4
        const val ID_CHARTS = 5
        const val ID_CALENDAR = 6
        const val ID_CLASSMATES = 7
        const val ID_TEACHERS = 8
        const val ID_SCHOOL_NEWS = 9
        const val ID_SUPPORT = 10
        const val ID_SETTINGS = 11
    }
}