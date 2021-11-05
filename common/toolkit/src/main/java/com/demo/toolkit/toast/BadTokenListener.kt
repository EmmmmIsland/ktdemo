package com.demo.toolkit.toast

import android.widget.Toast

interface BadTokenListener {
    fun onBadTokenCaught(toast: Toast)
}