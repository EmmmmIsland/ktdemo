package com.hellokt.network

import com.hellokt.network.cert.TrustAllManager
import com.ebook.network.http.StethoManager
import com.hellokt.network.http.interceptor.HeaderInterceptor
//import com.ebook.toolkit.App
//import com.ebook.toolkit.io.FileUtils
import com.demo.toolkit.json.JsonUtil
import com.ebook.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object NetWorkHelper {
    private val okHttpClient: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()

        // 证书
        if (!BuildConfig.DEBUG) {
            val trustAllManager = TrustAllManager()
            TrustAllManager.createTrustAllSSLFactory(trustAllManager)?.let {
                builder.sslSocketFactory(
                    it,
                    trustAllManager
                ).hostnameVerifier(TrustAllManager.createTrustAllHostnameVerifier())
            }
        }

        val stethoInterceptor: Interceptor? = StethoManager.stethoInterceptor()
        if (stethoInterceptor != null) {
            //一定要判断不为空，release不会初始化stetho，不要因为ide的提示把判空给删了
            builder.addNetworkInterceptor(stethoInterceptor)
        }
        builder.addInterceptor(HeaderInterceptor())
        builder.connectTimeout(60000L, TimeUnit.MILLISECONDS)
            .readTimeout(60000L, TimeUnit.MILLISECONDS)
            .build()
    }

//    val resourceOkHttpClient: OkHttpClient by lazy {
//        var builder = OkHttpClient.Builder()
//        builder.cache(
//            Cache(FileUtils.getTempDir(App.INSTANCE, "resource").apply {
//                FileUtils.mkdirs(this)
//            }, 1 * 1024 * 1024)
//        )
//        builder.build()
//    }

    val retrofit: Retrofit by lazy {
        val baseUrl = Api.getBaseUrl()
        Retrofit.Builder()
            .baseUrl(
                baseUrl
            )
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(JsonUtil.gson))
            .build()
    }

}