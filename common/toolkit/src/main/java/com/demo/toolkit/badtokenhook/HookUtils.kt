package com.demo.toolkit.badtokenhook

import android.annotation.SuppressLint
import android.os.Build
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentHostCallback

object HookUtils {
    private val TAG = HookUtils::class.java.simpleName

    /**
     * @param toast Toast
     * @return 是否Hook成功
     */
    fun maybeHookToastHandler(toast: Toast): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                Build.VERSION.SDK_INT <= Build.VERSION_CODES.N &&
                hookToastHandler(toast)
    }

    /**
     * 替换 Toast对象中的Handler实例，使之消息处理时，可以捕获异常。
     *
     * 原因：
     * Android 5.0-7.1.2系统中，显示Toast如果超时，会触发WindowManager$BadTokenException异常。
     * Android 8.0的Toast类实现，把这个异常捕获并忽略了。因此可以参考这一思路来解决这个问题。
     *
     * @param toast Toast
     * @return 是否hook成功
     */
    @SuppressLint("SoonBlockedPrivateApi")
    private fun hookToastHandler(toast: Toast): Boolean {
        try {
            val mTN = Toast::class.java.getDeclaredField("mTN").let {
                it.isAccessible = true
                it.get(toast)
            }
            val fieldHandler = mTN.javaClass.getDeclaredField("mHandler").also {
                it.isAccessible = true
            }

            val originHandler = fieldHandler.get(mTN) as Handler
            fieldHandler.set(mTN, HookHandler(originHandler))
            return true
        } catch (e: Throwable) {
            Log.e(TAG, "hook error", e)
            return false
        }
    }

    /**
     * 替换[FragmentActivity]中的[Handler]实例，使之消息处理时，可以捕获异常。
     *
     * 原因：
     * Fragment的commit是提交到这个Handler中异步操作的，若真正到执行时Window已经不存在，
     * 可能会抛出
     */
    fun hookTokenHandler(activity: FragmentActivity): Boolean {
        try {
            val mFragments = FragmentActivity::class.java.getDeclaredField("mFragments").let {
                it.isAccessible = true
                it.get(activity)
            }
            val mHost = mFragments.javaClass.getDeclaredField("mHost").let {
                it.isAccessible = true
                it.get(mFragments)
            }
            val fieldHandler = FragmentHostCallback::class.java.getDeclaredField("mHandler").also {
                it.isAccessible = true
            }

            val originHandler = fieldHandler.get(mHost) as Handler
            fieldHandler.set(mHost, HookHandler(originHandler))

            return true
        } catch (tr: Throwable) {
            Log.e(TAG, "hook error", tr)
            return false
        }
    }
}