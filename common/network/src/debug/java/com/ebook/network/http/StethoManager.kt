package com.ebook.network.http

import okhttp3.Interceptor

object StethoManager {

    private var flipperOkhttpInterceptor: Interceptor? = null
    fun initStetho(flipperOkhttpInterceptor: Interceptor) {
        this.flipperOkhttpInterceptor = flipperOkhttpInterceptor
    }

    fun stethoInterceptor(): Interceptor? {
        return flipperOkhttpInterceptor
    }
}