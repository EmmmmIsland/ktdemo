package com.hellokt.network.http

import java.util.*

object RequestUtil {

    const val code = "code"
    const val message = "message"
    const val body = "body"

    /**
     * 公共头部
     */
    fun getHeader(): MutableMap<String, String> {
//        if(UserInfoHolder.isLogin()) {
//            return mutableMapOf(
//                Pair("Content-Type", "application/json;charset=UTF-8"),
//                Pair("Authorization", if (TextUtils.isEmpty(UserInfoHolder.getToken())) "" else "Basic " + UserInfoHolder.getToken()),
//                Pair("osVersion", Build.VERSION.RELEASE),
//                Pair("userId", UserInfoHolder.getUserId()),
//                Pair("osType", "Android"),
//            )
//        } else {
//            return mutableMapOf(
//                Pair("Content-Type", "application/json;charset=UTF-8"),
//                Pair("Authorization", if (TextUtils.isEmpty(UserInfoHolder.getToken())) "" else "Basic " + UserInfoHolder.getToken()),
//                Pair("osVersion", Build.VERSION.RELEASE),
//                Pair("guestId", UserInfoHolder.getUserId()),
//                Pair("osType", "Android"),
//            )
//        }
        return mutableMapOf()
    }

    /**
     * 公共请求参数
     * 兼容参数value值为json格式
     */
    fun getBodyAny(body: MutableMap<String, Any>): MutableMap<String, Any> {
        return body
    }

    private fun getUuid(): String {
        return UUID.randomUUID().toString().replace("-".toRegex(), "")
    }
}