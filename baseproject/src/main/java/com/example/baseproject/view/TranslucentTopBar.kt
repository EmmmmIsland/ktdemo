package com.example.baseproject.view

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.annotation.Px
import com.example.baseproject.R
import java.lang.Exception

class TranslucentTopBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private var mHeight = 0
    private var mBackgroundResourceId = 0
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        try {
            if (mHeight > 0) {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY)
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        } catch (e: Exception) {
        }
    }

    /**
     * 设置自定义高度
     *
     * @param height     高度
     * @param paddingTop 顶部高度
     */
    fun setCustomHeight(@Px height: Int, @Px paddingTop: Int) {
        if (height > 0) {
            mHeight = height
            setPadding(0, paddingTop, 0, 0)
            requestLayout()
        }
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.TranslucentTopBar)
        mBackgroundResourceId = array.getResourceId(
            R.styleable.TranslucentTopBar_translucenttopbar_backgroud,
            R.drawable.tool_bar_bg
        )
        mHeight = resources.getDimensionPixelSize(R.dimen.top_bar_height)
        setPadding(0, resources.getDimensionPixelSize(R.dimen.top_bar_padding_top), 0, 0)
        setBackgroundResource(mBackgroundResourceId)
    }

    init {
        init(context, attrs)
    }
}