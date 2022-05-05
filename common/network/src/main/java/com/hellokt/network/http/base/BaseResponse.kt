package com.hellokt.network.http.base

import com.hellokt.network.RequestCode

abstract class BaseResponse {
    open val code: Int = 0
    open val message: String? = null
    abstract val data: Any?

    open fun isSuccess(): Boolean {
        return code == RequestCode.SUCCESS
    }
}