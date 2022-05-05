package com.hellokt.network.http.base

import com.hellokt.network.http.RequestMode
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType
import java.io.File
import kotlin.reflect.full.memberProperties

abstract class BaseRequest {

    abstract fun api(): String

    fun reflectParameters(): MutableMap<String, Any> {
        val params = mutableMapOf<String, Any>()
        val className = this.javaClass.kotlin

        className.memberProperties.forEach {
            var fieldSerializedName: SerializedName? = null
            try {
                val field = className.java.getDeclaredField(it.name)
                fieldSerializedName = field.getAnnotation(SerializedName::class.java)
            } catch (ignored: NoSuchFieldException) {
            }

            val value: Any? = it.get(this)
            if (value != null && value.toString().isNotEmpty()) {
                params[fieldSerializedName?.value ?: it.name] = value
            }
        }
        params.apply {
            remove("file")
            remove("callBackUrl")
        }
        return params
    }

    open fun requestMode(): RequestMode {
        return RequestMode.GET
    }

    var file: File? = null

    var callBackUrl: String = ""

    open fun contentType(): MediaType? {
        return null
    }

    open fun useGzip(): Boolean {
        return true
    }

    open fun useSignatureParams(): Boolean {
        return false
    }

    open fun signatureParams(params: MutableMap<String, Any>) {}
}