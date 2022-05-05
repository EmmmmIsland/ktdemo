package com.hellokt.network.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    companion object {

        var headerProvider: HeaderProvider? = null

        const val KEY_USER_AGENT = "User-Agent";

        const val KEY_AUTH_TOKEN = "X-Auth-Token"
        const val KEY_AUTH_TIMESTAMP = "X-Auth-Timestamp"
        const val KEY_AUTH_SIGNATURE = "X-Auth-Signature"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        headerProvider?.let { headerProvider ->

            builder.header(KEY_USER_AGENT, headerProvider.userAgent())

            val authToken = headerProvider.getAuthToken()
            if (!authToken.isNullOrEmpty()) {
                builder.header(KEY_AUTH_TOKEN, authToken)
                val timeStamp = headerProvider.getAuthTimestampSeconds()
                builder.header(KEY_AUTH_TIMESTAMP, timeStamp)
                val signatureText = chain.request().url.toUrl().file + '&' + timeStamp
                val authSignature = headerProvider.getAuthSignature(signatureText)
                builder.header(KEY_AUTH_SIGNATURE, authSignature)
            }
            builder.header("X-User-Userid", headerProvider.getUserId() ?: "")
        }
        return chain.proceed(builder.build())
    }

}