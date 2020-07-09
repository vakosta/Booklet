package com.booklet.bookletandroid.presentation

import android.app.Activity
import android.content.Context

abstract class SafeFragment : androidx.fragment.app.Fragment() {
    var aeListener: IActivityEnabledListener? = null

    interface IActivityEnabledListener {
        fun onActivityEnabled(activity: androidx.fragment.app.FragmentActivity)
    }

    inline fun activityEnabled(crossinline action: (activity: androidx.fragment.app.FragmentActivity) -> Unit):
            IActivityEnabledListener {
        aeListener = object : IActivityEnabledListener {
            override fun onActivityEnabled(activity: androidx.fragment.app.FragmentActivity) {
                action(activity)
            }
        }
        if (activity != null)
            aeListener!!.onActivityEnabled(requireActivity())
        return aeListener!!
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (aeListener != null) {
            aeListener!!.onActivityEnabled(context as androidx.fragment.app.FragmentActivity)
            aeListener = null
        }
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        if (aeListener != null) {
            aeListener!!.onActivityEnabled(activity as androidx.fragment.app.FragmentActivity)
            aeListener = null
        }
    }
}