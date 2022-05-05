package com.hellokt.network.http.base

import com.demo.toolkit.ext.nullOr
import com.hellokt.network.http.RequestMode

class RequestUrl(private val callback: String?): BaseRequest() {

    override fun api(): String {
        return callback.nullOr("")
    }

    override fun requestMode(): RequestMode {
        return RequestMode.GET
    }

    override fun useSignatureParams(): Boolean {
        return true
    }

}