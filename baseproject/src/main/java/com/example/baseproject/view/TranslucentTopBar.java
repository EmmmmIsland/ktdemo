package com.example.baseproject.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import androidx.annotation.Px;

import com.example.baseproject.R;

public class TranslucentTopBar extends RelativeLayout {

    private int mHeight;
    private int mBackgroundResourceId;

    public TranslucentTopBar(Context context) {
        this(context, null);
    }

    public TranslucentTopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TranslucentTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            if (mHeight > 0) {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY);
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } catch (Exception e) {}
    }

    /**
     * 设置自定义高度
     *
     * @param height     高度
     * @param paddingTop 顶部高度
     */
    public void setCustomHeight(@Px int height, @Px int paddingTop) {
        if (height > 0) {
            mHeight = height;
            setPadding(0, paddingTop, 0, 0);
            requestLayout();
        }
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TranslucentTopBar);
        mBackgroundResourceId = array.getResourceId(R.styleable.TranslucentTopBar_translucenttopbar_backgroud, R.drawable.tool_bar_bg);
        mHeight = getResources().getDimensionPixelSize(R.dimen.top_bar_height);
        setPadding(0, getResources().getDimensionPixelSize(R.dimen.top_bar_padding_top), 0, 0);
        setBackgroundResource(mBackgroundResourceId);
    }
}
