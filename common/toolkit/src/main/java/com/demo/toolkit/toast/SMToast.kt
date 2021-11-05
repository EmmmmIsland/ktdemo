package com.demo.toolkit.toast

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import com.demo.toolkit.ext.nullOr
import com.ebook.toolkit.R

class SMToast {
    companion object {

        @JvmStatic
        fun makeText(context: Context, text: CharSequence?, duration: Int): Toast {
            val smToast = SMToast(context, text, duration)
            return smToast.toast
        }

        @JvmStatic
        fun makeText(context: Context, @StringRes resId: Int, duration: Int): Toast {
            val smToast = SMToast(context, resId, duration)
            return smToast.toast
        }
    }

    private var toast: Toast

    private constructor(context: Context, text: CharSequence?, duration: Int) {
        val view = LayoutInflater.from(context).inflate(R.layout.framework_view_smtoast, null)
        val tvMessage = view?.findViewById<TextView>(R.id.tv_message_frawork_view_smtoast)
        tvMessage?.text = text.nullOr("")
        toast = Toast(context)
        toast.duration = duration
        toast.view = view
    }

    private constructor(context: Context, @StringRes resId: Int, duration: Int) : this(context, context.resources.getString(resId), duration)
}