package com.demo.toolkit.glide

import android.content.res.Resources
import android.graphics.*
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import java.security.MessageDigest

class GlideCircleTransformWithBorder : BitmapTransformation {
    private val ID = "GlideCircleTransformWithBorder"
    private val ID_BYTES = ID.toByteArray()
    private var mBorderPaint: Paint? = null
    private var mBorderWidth = 0f

    constructor() : super() {}
    constructor(borderWidth: Int, borderColor: Int) : super() {
        mBorderWidth = Resources.getSystem().displayMetrics.density * borderWidth
        mBorderPaint = Paint()
        mBorderPaint!!.isDither = true
        mBorderPaint!!.isAntiAlias = true
        mBorderPaint!!.color = borderColor
        mBorderPaint!!.style = Paint.Style.STROKE
        mBorderPaint!!.strokeWidth = mBorderWidth
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        return circleCrop(pool, toTransform)!!
    }

    private fun circleCrop(pool: BitmapPool, source: Bitmap?): Bitmap? {
        if (source == null) {
            return null
        }
        val size = (Math.min(source.width, source.height) - mBorderWidth / 2).toInt()
        val x = (source.width - size) / 2
        val y = (source.height - size) / 2
        // TODO this could be acquired from the pool too
        val squared = Bitmap.createBitmap(source, x, y, size, size)
        var result: Bitmap? = pool[size, size, Bitmap.Config.ARGB_8888]
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        }
        val canvas = Canvas(result!!)
        val paint = Paint()
        paint.shader = BitmapShader(
            squared,
            Shader.TileMode.CLAMP,
            Shader.TileMode.CLAMP
        )
        paint.isAntiAlias = true
        val r = size / 2f
        canvas.drawCircle(r, r, r, paint)
        if (mBorderPaint != null) {
            val borderRadius = r - mBorderWidth / 2
            canvas.drawCircle(r, r, borderRadius, mBorderPaint!!)
        }
        return result
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }
}