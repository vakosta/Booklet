package me.annenkov.julistaandroid.presentation

import android.app.Activity
import android.content.Context
import android.support.v4.app.FragmentActivity

abstract class SafeFragment : ViewPagerFragment() {
    var aeListener: IActivityEnabledListener? = null

    interface IActivityEnabledListener {
        fun onActivityEnabled(activity: FragmentActivity)
    }

    inline fun activityEnabled(crossinline action: (activity: FragmentActivity) -> Unit):
            IActivityEnabledListener {
        aeListener = object : IActivityEnabledListener {
            override fun onActivityEnabled(activity: FragmentActivity) {
                action(activity)
            }
        }
        if (activity != null)
            aeListener!!.onActivityEnabled(activity!!)
        return aeListener!!
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (aeListener != null) {
            aeListener!!.onActivityEnabled(context as FragmentActivity)
            aeListener = null
        }
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)

        if (aeListener != null) {
            aeListener!!.onActivityEnabled(activity as FragmentActivity)
            aeListener = null
        }
    }
}