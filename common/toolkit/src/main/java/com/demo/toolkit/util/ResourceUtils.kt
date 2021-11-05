package com.demo.toolkit.util

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.TouchDelegate
import android.view.View
import androidx.annotation.*
import androidx.core.text.BidiFormatter
import androidx.core.text.TextDirectionHeuristicsCompat
import com.demo.toolkit.App
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException
import java.lang.StringBuilder
import java.util.*

object ResourceUtils {
    private val context: Context?
        private get() = App.INSTANCE

    /**
     * Application context为空时，返回空字符串，增加健壮性
     */
    fun getQuantityString(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any?): String {
        return if (context != null) context!!
            .resources.getQuantityString(id, quantity, *formatArgs) else ""
    }

    /**
     * Application context为空时，返回空字符串，增加健壮性
     */
    fun getQuantityString(@PluralsRes id: Int, count: Int): String {
        return if (context != null) context!!
            .resources.getQuantityString(id, count, count) else ""
    }

    /**
     * Application context为空时，返回空字符串，增加健壮性
     */
    fun getString(@StringRes resId: Int): String {
        return if (context != null) context!!.resources.getString(resId) else ""
    }

    /**
     * Application context为空时，返回-1
     */
    fun getInt(@IntegerRes resId: Int): Int {
        return if (context != null) context!!.resources.getInteger(resId) else -1
    }

    fun getDimension(@DimenRes resId: Int): Float {
        return if (context != null) context!!.resources.getDimension(resId) else -1f
    }

    fun getDimensionPixelSize(@DimenRes resId: Int): Int {
        return if (context != null) context!!.resources.getDimensionPixelSize(resId) else -1
    }

    fun getFraction(@FractionRes resId: Int): Float {
        return getFraction(resId, 1)
    }

    fun getFraction(@FractionRes resId: Int, base: Int): Float {
        return getFraction(resId, base, base)
    }

    fun getFraction(@FractionRes resId: Int, base: Int, pbase: Int): Float {
        return if (context != null) context!!.resources.getFraction(resId, base, pbase) else 0f
    }

    /**
     * Application context为空时，返回空字符串，增加健壮性
     */
    fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String {
        try {
            return if (context != null) context!!.resources.getString(resId, *formatArgs) else ""
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getStringArray(@ArrayRes strArrayId: Int): Array<String> {
        try {
            return if (context != null) context!!.resources.getStringArray(strArrayId) else arrayOf()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return arrayOf()
    }

    fun getIntArray(@ArrayRes intArrayId: Int): IntArray {
        try {
            return if (context != null) context!!.resources.getIntArray(intArrayId) else intArrayOf()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return intArrayOf()
    }

    /**
     * Application context为空时，返回黑色，增加健壮性
     */
    fun getColor(@ColorRes resId: Int): Int {
        try {
            if (resId == -1) {
                return Color.BLACK
            }
            return if (context != null) context!!.resources.getColor(resId) else Color.BLACK
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Color.BLACK
    }

    @ColorInt
    fun getColorByAttributeId(context: Context?, @AttrRes attrIdForColor: Int): Int {
        val typedValue = TypedValue()
        var theme: Resources.Theme? = null
        if (context != null) {
            theme = context.theme
        }
        theme?.resolveAttribute(attrIdForColor, typedValue, true)
        return typedValue.data
    }

    /**
     * Application context为空时，返回透明图，增加健壮性
     */
    fun getDrawable(@DrawableRes resId: Int): Drawable {
        try {
            return if (context != null) context!!.resources.getDrawable(resId) else ColorDrawable(
                Color.TRANSPARENT
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ColorDrawable(Color.TRANSPARENT)
    }

    fun getBitmap(@DrawableRes resId: Int): Bitmap? {
        if (context != null) {
            val drawable = context!!.resources.getDrawable(resId)
            if (drawable is BitmapDrawable) {
                return drawable.bitmap
            }
        }
        return null
    }

    fun getBitmapForDensity(@DrawableRes resId: Int, density: Int): Bitmap? {
        if (context != null) {
            val drawable = context!!.resources.getDrawableForDensity(resId, density)
            if (drawable is BitmapDrawable) {
                return drawable.bitmap
            }
        }
        return null
    }

    @DrawableRes
    fun getDrawableIdByName(resName: String?): Int {
        var resId = 0
        if (context != null) {
            try {
                resId =
                    context!!.resources.getIdentifier(resName, "drawable", context!!.packageName)
            } catch (e: Exception) {
            }
        }
        return resId
    }

    /**
     * dip转换成px
     *
     * @param dipValue
     * @return
     */
    fun dip2px(dipValue: Float): Int {
        val context = context ?: return 0
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    fun dip2px(dipValue: Int): Int {
        val context = context ?: return 0
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    fun sp2px(spValue: Float): Float {
        val fontScale = App.INSTANCE.resources.displayMetrics.scaledDensity
        return spValue * fontScale + 0.5f
    }

    val density: Float
        get() {
            val context = context ?: return 0f
            val displayMetrics = ResourceUtils.context!!.resources.displayMetrics
            return displayMetrics.density
        }
    val densityDpi: Int
        get() {
            val context = context ?: return 0
            val displayMetrics = ResourceUtils.context!!.resources.displayMetrics
            return displayMetrics.densityDpi
        }
    val deviceDensity: String
        get() {
            val displayMetrics = context!!.resources.displayMetrics
            val density = displayMetrics.density
            val deviceDensity: String
            deviceDensity = if (density <= 1) {
                "mdpi"
            } else if (density < 2) {
                "hdpi"
            } else if (density < 2.5) {
                "xhdpi"
            } else if (density <= 3) {
                "xxhdpi"
            } else {
                "xxxhdpi"
            }
            return deviceDensity
        }
    val layoutInflater: LayoutInflater
        get() = LayoutInflater.from(context)

    fun getResourceUri(@DrawableRes resId: Int): String {
        val resources = context!!.resources
        return Uri.parse(
            String.format(
                "%s://%s/%s/%s",
                ContentResolver.SCHEME_ANDROID_RESOURCE,
                resources.getResourcePackageName(resId),
                resources.getResourceTypeName(resId),
                resources.getResourceEntryName(resId)
            )
        ).toString()
    }

    fun copy(label: String?, content: String?) {
        val clipboardManager =
            context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager?.setPrimaryClip(ClipData.newPlainText(label, content))
    }

    fun clearClipboard() {
        val clipboardManager =
            context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager?.setPrimaryClip(ClipData.newPlainText(null, null))
    }

    fun paste(): String {
        try {
            val clipboardManager =
                context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            if (clipboardManager != null && clipboardManager.hasPrimaryClip()) {
                val primaryClip = clipboardManager.primaryClip
                if (primaryClip != null && primaryClip.itemCount > 0) {
                    val item = primaryClip.getItemAt(0)
                    if (item != null && item.text != null) {
                        return item.text.toString()
                    }
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return ""
    }

    //判断是否镜像
    val isLayoutRtl: Boolean
        get() {
            val mConfig = context!!.resources.configuration
            return mConfig.layoutDirection == View.LAYOUT_DIRECTION_RTL
        }// return Locale.getDefault().getLanguage().equalsIgnoreCase("ar");

    //判断是否是右到左语言
    val isLanguageRtl: Boolean
        get() {
            // return Locale.getDefault().getLanguage().equalsIgnoreCase("ar");
            val ch = Locale.getDefault().displayName[0]
            val directionality = Character.getDirectionality(ch).toInt()
            return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT.toInt() || directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC.toInt()
        }
    private val BIDI_INSTANCE = BidiFormatter.getInstance(Locale.getDefault())

    //处理阿拉伯语下的混合文案,英文标点显示问题
    fun getRtlText(text: CharSequence): CharSequence {
        return if (isLanguageRtl) {
            BIDI_INSTANCE
                .unicodeWrap(text, TextDirectionHeuristicsCompat.ANYRTL_LTR)
        } else text
    }

    fun getRtlTexttFirstStrong(text: CharSequence): CharSequence {
        return if (isLanguageRtl) {
            BIDI_INSTANCE
                .unicodeWrap(text, TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR)
        } else text
    }

    fun getLocaleText(text: CharSequence?): CharSequence {
        return BIDI_INSTANCE.unicodeWrap(text, TextDirectionHeuristicsCompat.LOCALE)
    }

    /**
     * 扩大View的触摸和点击响应范围,最大不超过其父View范围
     *
     * @param view
     * @param top
     * @param bottom
     * @param left
     * @param right
     */
    fun expandViewTouchDelegate(
        view: View, top: Int,
        bottom: Int, left: Int, right: Int
    ) {
        (view.parent as View).post {
            val bounds = Rect()
            view.isEnabled = true
            view.getHitRect(bounds)
            bounds.top -= top
            bounds.bottom += bottom
            bounds.left -= left
            bounds.right += right
            val touchDelegate = TouchDelegate(bounds, view)
            if (View::class.java.isInstance(view.parent)) {
                (view.parent as View).touchDelegate = touchDelegate
            }
        }
    }

    fun getStatusbarHight(activity: Activity): Int {
        var statusHeight = 0
        val localRect = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(localRect)
        statusHeight = localRect.top
        if (0 == statusHeight) {
            val localClass: Class<*>
            try {
                localClass = Class.forName("com.android.internal.R\$dimen")
                val localObject = localClass.newInstance()
                val i5 = localClass.getField("status_bar_height")[localObject].toString().toInt()
                statusHeight = activity.resources.getDimensionPixelSize(i5)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: SecurityException) {
                e.printStackTrace()
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            }
        }
        return if (statusHeight == 0) {
            dip2px(25)
        } else statusHeight
    }
}
