package com.booklet.bookletandroid.presentation.fragments.events

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.booklet.bookletandroid.R
import com.booklet.bookletandroid.domain.Preferences
import com.booklet.bookletandroid.presentation.model.event.Filter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_event_filter.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.backgroundResource

class EventFilterFragment : BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event_filter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val prefs = Preferences.getInstance(requireActivity())

        changeFilterState(gradeEventFilter, prefs.filterGrade)
        setEventIcon(gradeEventFilter, R.drawable.account_group_outline)

        changeFilterState(newMarksEventFilter, prefs.filterNewMarks)
        setEventIcon(newMarksEventFilter, R.drawable.mark_outline)

        changeFilterState(testEventFilter, prefs.filterTests)
        setEventIcon(testEventFilter, R.drawable.schedule_outline)

        changeFilterState(holidaysEventFilter, prefs.filterHolidays)
        setEventIcon(holidaysEventFilter, R.drawable.face_agent)

        gradeEventFilter.setOnClickListener {
            val isEnable = !prefs.filterGrade
            prefs.filterGrade = isEnable
            changeFilterState(it, isEnable)
        }
        newMarksEventFilter.setOnClickListener {
            val isEnable = !prefs.filterNewMarks
            prefs.filterNewMarks = isEnable
            changeFilterState(it, isEnable)
        }
        testEventFilter.setOnClickListener {
            val isEnable = !prefs.filterTests
            prefs.filterTests = isEnable
            changeFilterState(it, isEnable)
        }
        holidaysEventFilter.setOnClickListener {
            val isEnable = !prefs.filterHolidays
            prefs.filterHolidays = isEnable
            changeFilterState(it, isEnable)
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        EventBus.getDefault().post(Filter(Filter.State.CLOSED))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog

            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
            bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN)
                        dismiss()
                }
            })
        }

        return dialog
    }

    private fun setEventIcon(view: View, icon: Int) {
        view.findViewById<ImageView>(R.id.eventIcon).setImageResource(icon)
    }

    private fun changeFilterState(view: View, isEnable: Boolean) {
        EventBus.getDefault().post(Filter(Filter.State.CLOSED))
        if (isEnable)
            enableFilter(view)
        else
            disableFilter(view)
    }

    private fun enableFilter(view: View) {
        val typedValue = TypedValue()
        val theme = requireContext().theme
        theme.resolveAttribute(R.attr.colorEventFilterIconEnable, typedValue, true)

        view.findViewById<ImageView>(R.id.eventIcon)
                .setColorFilter(ContextCompat.getColor(requireActivity(), typedValue.resourceId),
                        android.graphics.PorterDuff.Mode.SRC_IN)
        view.backgroundResource = R.drawable.background_event_filter_enable
    }

    private fun disableFilter(view: View) {
        val typedValue = TypedValue()
        val theme = requireContext().theme
        theme.resolveAttribute(R.attr.colorEventFilterIconDisable, typedValue, true)

        view.findViewById<ImageView>(R.id.eventIcon)
                .setColorFilter(ContextCompat.getColor(requireActivity(), typedValue.resourceId),
                        android.graphics.PorterDuff.Mode.SRC_IN)
        view.backgroundResource = R.drawable.background_event_filter_disable
    }
}