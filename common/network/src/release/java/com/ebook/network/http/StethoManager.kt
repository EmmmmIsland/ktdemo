package com.ebook.network.http

import android.app.Application
import okhttp3.Interceptor

object StethoManager {
    fun initStetho(flipperOkhttpInterceptor: Interceptor) {
    }

    fun stethoInterceptor(): Interceptor? {
        return null
    }
}