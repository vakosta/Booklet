package com.booklet.bookletandroid.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import com.booklet.bookletandroid.domain.Utils

class CustomFrameLayout : FrameLayout {
    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr)

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return Utils.convertPixelsToDp(context, ev.y) <= 56
    }
}


/*

template<class Layout, int MaxDP>
class CustomLayout: Layout {
    ....
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return Utils.convertPixelsToDp(context, ev.y) <= MaxDP
    }
};

typedef CustomLayout<FrameLayout> CustomFrameLayout;




 */