package com.demo.toolkit.ext

import android.content.Context

fun Context.dp2px(dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun Context.px2dp(pxValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

object AndroidBarHeight {
    var statusBarHeight: Int = 0
    var navigationBarHeight: Int = 0
    var navigationBarShow: Boolean? = null
}