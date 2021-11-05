package com.demo.toolkit.ext

import android.view.Gravity
import android.widget.Toast
import androidx.annotation.StringRes
import com.demo.toolkit.App
import com.demo.toolkit.badtokenhook.HookUtils
import com.demo.toolkit.toast.SMToast
import com.demo.toolkit.toast.ToastCompat
import com.demo.toolkit.util.SystemUtils

fun toast(msg: String?) {
    msg ?: return
    if (isN_MR1()) {
        ToastCompat.makeText(App.INSTANCE, msg, Toast.LENGTH_SHORT).show()
    } else {
        val toast = SMToast.makeText(App.INSTANCE, msg, Toast.LENGTH_SHORT)
        HookUtils.maybeHookToastHandler(toast)
        toast.showSafe()
    }
}

fun toastLong(msg: String) {
    if (isN_MR1()) {
        ToastCompat.makeText(App.INSTANCE, msg, Toast.LENGTH_LONG).show()
    } else {
        val toast = SMToast.makeText(App.INSTANCE, msg, Toast.LENGTH_LONG)
        HookUtils.maybeHookToastHandler(toast)
        toast.showSafe()
    }
}

fun toast(@StringRes resId: Int) {
    if (isN_MR1()) {
        ToastCompat.makeText(App.INSTANCE, resId, Toast.LENGTH_SHORT).show()
    } else {
        val toast = SMToast.makeText(App.INSTANCE, resId, Toast.LENGTH_SHORT)
        HookUtils.maybeHookToastHandler(toast)
        toast.showSafe()
    }
}

fun toastLong(@StringRes resId: Int) {
    if (isN_MR1()) {
        ToastCompat.makeText(App.INSTANCE, resId, Toast.LENGTH_LONG).show()
    } else {
        val toast = SMToast.makeText(App.INSTANCE, resId, Toast.LENGTH_LONG)
        HookUtils.maybeHookToastHandler(toast)
        toast.showSafe()
    }
}

fun toastCenter(string: String) {
    if (isN_MR1()) {
        ToastCompat.makeText(App.INSTANCE, string, Toast.LENGTH_SHORT).let {
            it.setGravity(Gravity.CENTER, 0, 0)
            it.show()
        }
    } else {
        SMToast.makeText(App.INSTANCE, string, Toast.LENGTH_SHORT).let {
            HookUtils.maybeHookToastHandler(it)
            it.setGravity(Gravity.CENTER, 0, 0)
            it.showSafe()
        }
    }
}

fun toastLong(msg: String, gravity: Int, xOffset: Int = 0, yOffset: Int = 0) {
    if (isN_MR1()) {
        ToastCompat.makeText(App.INSTANCE, msg, Toast.LENGTH_LONG).let {
            it.setGravity(gravity, xOffset, yOffset)
            it.show()
        }
    } else {
        val toast = SMToast.makeText(App.INSTANCE, msg, Toast.LENGTH_LONG)
        toast.setGravity(gravity, xOffset, yOffset)
        HookUtils.maybeHookToastHandler(toast)
        toast.showSafe()
    }
}

private fun Toast.showSafe() {
    try {
        this.show()
    } catch (e: Exception) {
        // pass
    }
}


private fun isN_MR1(): Boolean {
    return SystemUtils.isN_MR1()
}
