package me.annenkov.julistaandroid.presentation.customviews

import android.support.v4.view.ViewPager
import android.view.View

class RotateDownTransformer : ViewPager.PageTransformer {
    private fun inRange(position: Float): Boolean {
        return position <= 1.0 && position >= -1.0
    }

    private fun isLeftPage(position: Float): Boolean {
        return position < 0
    }

    private fun isRightPage(position: Float): Boolean {
        return position > 0
    }

    override fun transformPage(page: View, position: Float) {
        page.pivotY = page.height.toFloat()

        if (inRange(position)) {
            when {
                isRightPage(position) -> {
                    val rotation = -position * MAX_ROTATION

                    page.pivotX = 0f
                    page.rotation = rotation
                }
                isLeftPage(position) -> {
                    val rotation = -position * MAX_ROTATION

                    page.pivotX = page.width.toFloat()
                    page.rotation = rotation
                }
                else -> page.rotation = 0f
            }
        }
    }

    companion object {
        private const val MAX_ROTATION = -9f
    }
}