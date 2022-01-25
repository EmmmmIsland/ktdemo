package com.demo.toolkit.glide

import android.graphics.*
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class GlideRoundTransform(private val radius: Int) : BitmapTransformation() {

    private val ID = "com.baoerzixun.app.toolkit.GlideRoundTransform"
    private val ID_BYTES: ByteArray = ID.toByteArray(Key.CHARSET)

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap? {
        return roundCrop(pool, toTransform)
    }

    private fun roundCrop(pool: BitmapPool, source: Bitmap): Bitmap {
        var result = pool.get(source.width, source.height, Bitmap.Config.ARGB_8888)
        if (result == null) {
            result = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        }
        //这时result只是一张指定了大小的空图，如果要求不高的话可以更改ARGB_8888以减少内存消耗
        val canvas = Canvas(result)
        val paint = Paint()
        paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.isAntiAlias = true
        val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
        canvas.drawRoundRect(rectF, radius.toFloat(), radius.toFloat(), paint)
        //操作其实很简单就是画了一个圆角矩形
        clear(canvas)
        return result
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    private fun clear(canvas: Canvas) {
        canvas.setBitmap(null)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GlideRoundTransform

        if (radius != other.radius) return false
        if (ID != other.ID) return false
        if (!ID_BYTES.contentEquals(other.ID_BYTES)) return false

        return true
    }
}