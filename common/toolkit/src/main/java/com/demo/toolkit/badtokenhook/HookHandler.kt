package com.demo.toolkit.badtokenhook

import android.os.Handler
import android.os.Message
import android.util.Log

/**
 * 捕获Handler消息分发的异常
 */
class HookHandler(private val orig: Handler) : Handler() {
    override fun dispatchMessage(msg: Message) {
        try {
            orig.dispatchMessage(msg)
        } catch (e: Exception) {
            Log.e(TAG, "dispatchMessage error", e)
        }
    }

    companion object {
        private val TAG = HookHandler::class.java.simpleName
    }
}