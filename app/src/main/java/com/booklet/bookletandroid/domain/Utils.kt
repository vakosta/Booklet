package com.booklet.bookletandroid.domain

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue
import java.security.MessageDigest
import java.util.regex.Pattern

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    val digested = md.digest(toByteArray())
    return digested.joinToString("") {
        String.format("%02x", it)
    }
}

fun String.subscriptionHash(): String = this.md5().md5().md5()

fun Context.attribute(value: Int): TypedValue {
    val ret = TypedValue()
    theme.resolveAttribute(value, ret, true)
    return ret
}

object Utils {
    fun copyToClipboard(context: Context, label: String, data: String) {
        val clipboard = context
                .getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, data)
        clipboard.setPrimaryClip(clip)
    }

    fun convertPixelsToDp(context: Context, px: Float): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        val dp = px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        return dp
    }

    /**
     * Returns a list with all links contained in the input
     */
    fun extractUrls(text: String): List<String> {
        val containedUrls = ArrayList<String>()
        val urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?+-=\\\\.&]*)"
        val pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE)
        val urlMatcher = pattern.matcher(text)

        while (urlMatcher.find()) {
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)))
        }

        return containedUrls
    }
}