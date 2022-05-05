package com.demo.toolkit.json

import com.demo.toolkit.LogUtil
//import com.ebook.data.*
import com.demo.toolkit.json.format.DoubleTypeAdapter
import com.demo.toolkit.json.format.FloatTypeAdapter
import com.demo.toolkit.json.format.IntegerTypeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Type

/**
 * json转换工具类
 */
object JsonUtil {
    private val LOG_TAG = JsonUtil::class.java.name
    val gson: Gson = getFormatGson()

    fun <T> unpack(jsonString: String, clazz: Class<T>): T? {
        return try {
            gson.fromJson(jsonString, clazz)
        } catch (e: Exception) {
            LogUtil.e(LOG_TAG, e.localizedMessage)
            null
        }
    }

    fun <T> unpack(jsonString: String, type: Type): T? {
        return try {
            gson.fromJson(jsonString, type)
        } catch (e: Exception) {
            LogUtil.e(LOG_TAG, e.localizedMessage)
            null
        }
    }


    fun pack(o: Any?): String {
        if (o == null) {
            return ""
        }
        return try {
            gson.toJson(o)
        } catch (e: Exception) {
            LogUtil.e(LOG_TAG, e.localizedMessage)
            ""
        }
    }

    fun pack(o: Any?, type: Type): String {
        if (o == null) {
            return ""
        }
        return try {
            gson.toJson(o, type)
        } catch (e: Exception) {
            LogUtil.e(LOG_TAG, e.localizedMessage)
            ""
        }
    }

    private fun getFormatGson(): Gson {
        val builder = GsonBuilder()
        builder.registerTypeAdapter(Int::class.java, IntegerTypeAdapter())
        builder.registerTypeAdapter(Integer::class.java, IntegerTypeAdapter())
        builder.registerTypeAdapter(Double::class.java, DoubleTypeAdapter())
        builder.registerTypeAdapter(java.lang.Double::class.java, DoubleTypeAdapter())
        builder.registerTypeAdapter(Float::class.java, FloatTypeAdapter())
        builder.registerTypeAdapter(java.lang.Float::class.java, FloatTypeAdapter())
        return builder.create()
    }
}