package com.demo.toolkit

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.jeremyliao.liveeventbus.BuildConfig
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @author zengkebing
 * @since 2013-4-25
 */
object LogUtil {
    private const val TAG = "StarMaker"

    /** Debug开关  */
    val DEBUG = BuildConfig.DEBUG
    private val LOGV = DEBUG
    private val LOGD = DEBUG
    private val LOGI = DEBUG
    private val LOGW = DEBUG
    private val LOGE = DEBUG
    private const val TAG_CONTENT_PRINT = "%s:%s.%s:%d"

    /**
     * log for berbose
     *
     * @param tag
     * log tag
     * @param msg
     * log msg
     */
    @JvmStatic
    fun v(tag: String?, msg: String?) { // NO_UCD (use default)
        if (LOGV) {
            if (TextUtils.isEmpty(tag)) {
                Log.v(TAG, getContent(currentStackTraceElement) + "->" + msg)
            } else {
                Log.v(tag, getContent(currentStackTraceElement) + "->" + msg)
            }
        }
    }

    /**
     * log for berbose
     *
     * @param msg
     * log msg
     */
    @JvmStatic
    fun v(msg: String?) {
        v(TAG, msg)
    }

    /**
     * log for debug
     *
     * @param tag
     * log tag
     * @param msg
     * log msg
     */
    @JvmStatic
    fun d(tag: String?, msg: String?) {
        if (LOGD) {
            if (TextUtils.isEmpty(tag)) {
                Log.d(TAG, getContent(currentStackTraceElement) + "->" + msg)
            } else {
                Log.d(tag, getContent(currentStackTraceElement) + "->" + msg)
            }
        }
    }

    /**
     * log for debug
     *
     * @param msg
     * log msg
     */
    @JvmStatic
    fun d(msg: String?) {
        d(TAG, msg)
    }

    /**
     * log for information
     *
     * @param tag
     * log tag
     * @param msg
     * log msg
     */
    @JvmStatic
    fun i(tag: String?, msg: String?) {
        if (LOGI) {
            if (TextUtils.isEmpty(tag)) {
                Log.i(TAG, getContent(currentStackTraceElement) + "->" + msg)
            } else {
                Log.i(tag, getContent(currentStackTraceElement) + "->" + msg)
            }
        }
    }

    @JvmStatic
    fun i(tag: String?, msg: String?, vararg args: Any?) {
        if (LOGI) {
            Log.i(tag, getContent(currentStackTraceElement) + "->" + fmt(msg, args))
        }
    }

    /**
     * log for information
     *
     * @param msg
     * log msg
     */
    @JvmStatic
    fun i(msg: String?) {
        i(TAG, msg)
    }

    /**
     * log for warning
     *
     * @param tag
     * log tag
     * @param msg
     * log msg
     */
    @JvmStatic
    fun w(tag: String?, msg: String?) { // NO_UCD (unused code)
        var msg = msg
        if (LOGW) {
            if (TextUtils.isEmpty(msg)) {
                msg = ""
            }
            if (TextUtils.isEmpty(tag)) {
                Log.w(TAG, getContent(currentStackTraceElement) + "->" + msg)
            } else {
                Log.w(tag, getContent(currentStackTraceElement) + "->" + msg)
            }
        }
    }

    /**
     * log for warning
     *
     * @param msg
     * log msg
     */
    @JvmStatic
    fun w(msg: String?) {
        w(TAG, msg)
    }

    /**
     * log for error
     *
     * @param tag
     * log tag
     * @param msg
     * log msg
     */
    @JvmStatic
    fun e(tag: String?, msg: String?) {
        if (LOGE) {
            if (TextUtils.isEmpty(tag)) {
                Log.e(TAG, getContent(currentStackTraceElement) + "->" + msg)
            } else {
                Log.e(tag, getContent(currentStackTraceElement) + "->" + msg)
            }
        }
    }

    /**
     * log for error
     *
     * @param tag
     * log tag
     * @param msg
     * log msg
     */
    @JvmStatic
    fun e(tag: String?, msg: String?, e: Throwable) {
        if (LOGE) {
            if (TextUtils.isEmpty(tag)) {
                Log.e(TAG, getContent(currentStackTraceElement) + "->" + msg, e)
            } else {
                Log.e(tag, getContent(currentStackTraceElement) + "->" + msg, e)
            }
        }
    }

    /**
     * log for error
     *
     * @param msg
     * log msg
     */
    @JvmStatic
    fun e(msg: String?) {
        e(TAG, msg)
    }


    /**
     * cf
     */
    @JvmStatic
    fun mark() {
        if (DEBUG) {
            Log.w(TAG, getTAG())
        }
    }

    /**
     * cf
     *
     * @param str
     * msg
     */
    @JvmStatic
    fun mark(str: String) { // NO_UCD (unused code)
        if (DEBUG) {
            Log.w(TAG, getTAG() + "---" + str)
        }
    }

    /**
     * cf
     */
    @JvmStatic
    fun traces() {
        if (DEBUG) {
            val stacks = Thread.currentThread().stackTrace
            val sb = StringBuilder()
            if (stacks != null) {
                val NUMBER_3 = 3
                val NUMBER_4 = 4
                val NUMBER_15 = 15
                var ste = stacks[NUMBER_3]
                sb.append(
                    """
    ${ste.className}.${ste.methodName}#line=${ste.lineNumber}的调用：
    
    """.trimIndent()
                )
                var i = NUMBER_4
                while (i < stacks.size && i < NUMBER_15) {
                    ste = stacks[i]
                    sb.append(
                        (i - NUMBER_4)
                            .toString() + "--"
                                + ste.className
                                + "."
                                + ste.methodName
                                + "(...)#line:"
                                + ste.lineNumber
                                + "\n"
                    )
                    i++
                }
            }
            Log.w(TAG, getTAG() + "--" + sb.toString())
        }
    }

    /**
     * cf
     *
     * @return tag
     */
    @JvmStatic
    fun getTAG(): String { // NO_UCD (use private)
        // XXX this not work with proguard, maybe we cannot get the line number
        // with a proguarded jar file.
        // I add a try/catch as a quick fixing.
        return try {
            val NUMBER_4 = 4
            val NUMBER_5 = 5
            val stacks = Thread.currentThread().stackTrace
            val sb = StringBuilder()
            if (stacks != null) {
                val ste = stacks[NUMBER_4]
                sb.append(
                    ste.fileName.subSequence(0, ste.fileName.length - NUMBER_5)
                        .toString() + "."
                            + ste.methodName
                            + "#"
                            + ste.lineNumber
                )
            }
            sb.toString()
        } catch (e: NullPointerException) {
            "PROGUARDED"
        }
    }

    //获取LOG
    private fun getContent(trace: StackTraceElement): String {
        return String.format(
            TAG_CONTENT_PRINT, TAG,
            trace.className, trace.methodName,
            trace.lineNumber
        )
    }

    private val currentStackTraceElement: StackTraceElement
        private get() = Thread.currentThread().stackTrace[4]

    private fun fmt(msg: String?, vararg args: Any): String {
        val log: String = try {
            String.format(msg!!, *args)
        } catch (e: Exception) {
            return msg.toString()
        }
        return log
    }

    /**
     * 保存log到文件
     *
     * @param message
     * 要保存的数据
     */
    @JvmStatic
    fun saveLog(context: Context, message: String) {
        if (LOGD) {
            val log = """
                ${
                SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss:SSS    ",
                    Locale.ENGLISH
                ).format(Date())
            }$message
                
                """.trimIndent()
            try {
                val sdcard = context.externalCacheDir.toString()
                val f = File("$sdcard/starmaker.log")
                var fos: FileOutputStream? = FileOutputStream(f, true)
                fos!!.write(log.toByteArray())
                fos.close()
                fos = null
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}