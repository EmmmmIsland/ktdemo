package com.demo.toolkit.json.format

import com.demo.toolkit.LogUtil
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class FloatTypeAdapter : TypeAdapter<Number>() {
    override fun write(out: JsonWriter?, value: Number?) {
        out?.value(value)
    }

    override fun read(`in`: JsonReader?): Number? {
        if (`in` == null) {
            return null
        }
        if (`in`.peek() == JsonToken.NULL) {
            `in`.nextNull()
            return null
        }
        return try {
            val result = `in`.nextString()
            if ("" == result || "null".equals(result, ignoreCase = true)) {
                null
            } else result.toFloat()
        } catch (e: Exception) {
            LogUtil.e("parse Float fail", e.message)
            null
        }
    }
}