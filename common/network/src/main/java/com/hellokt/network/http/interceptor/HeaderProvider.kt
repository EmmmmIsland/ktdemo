package com.hellokt.network.http.interceptor

abstract class HeaderProvider {

    abstract fun getDeviceID(): String

    abstract fun getVersionName(): String

    abstract fun userAgent(): String

    abstract fun getAuthToken(): String?
    abstract fun getUserId(): String?

    /**
     * 计算签名结果
     * @param signatureText 需要签名的字符串
     */
    abstract fun getAuthSignature(signatureText: String): String

    /**
     * 时间戳信息，单位为秒
     */
    abstract fun getAuthTimestampSeconds(): String

}