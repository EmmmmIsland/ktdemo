package com.demo.toolkit.ext

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.*
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.demo.toolkit.util.ResourceUtils
import com.ebook.toolkit.R
import com.demo.toolkit.glide.GlideRoundTransform

internal object ViewClickSafe {
    var hash: Int = 0
    var lastClickTime: Long = 0
    var SPACE_TIME: Long = 600
}

fun View.onClickSafe(clickAction: () -> Unit) {
    this.setOnClickListener {
        if (this.hashCode() != ViewClickSafe.hash) {
            ViewClickSafe.hash = this.hashCode()
            ViewClickSafe.lastClickTime = System.currentTimeMillis()
            clickAction()
        } else {
            val currentTime = System.currentTimeMillis()
            if (currentTime - ViewClickSafe.lastClickTime > ViewClickSafe.SPACE_TIME) {
                ViewClickSafe.lastClickTime = System.currentTimeMillis()
                clickAction()
            }
        }
    }
}

fun ImageView.loadUrl(imgUrl: String?,placeHolder:Int = R.drawable.ic_default,errorPlaceholder:Int = R.drawable.ic_default) {
    if (imgUrl.isNullOrEmpty()) {
        return
    }
    Glide.with(this).load(imgUrl)
        .placeholder(placeHolder)
        .error(errorPlaceholder)
        .into(this)
}

fun ImageView.loadRoundImage(
    url: String?,
    radiusDp: Float,
    widthDp: Float = 0f,
    heightDp: Float = 0f,
    placeHolder:Int = R.drawable.ic_default_round,
    errorPlaceholder:Int = R.drawable.ic_default_round,
) {
    if (url.isNullOrEmpty()) {
        return
    }
    val drawableTypeRequest = Glide.with(context).load(url).placeholder(placeHolder).error(errorPlaceholder)
    if (widthDp > 0f && heightDp > 0f) {
        drawableTypeRequest.override(context.dp2px(widthDp), context.dp2px(heightDp))
    }
    drawableTypeRequest.fitCenter()
        .transform(CenterCrop(), GlideRoundTransform(context.dp2px(radiusDp)))
        .into(this)
}

fun ImageView.loadCircleImage(url: String?,placeHolder:Int = R.drawable.ic_default,errorPlaceholder:Int = R.drawable.ic_default){
    if (url.isNullOrEmpty()) {
        return
    }
    Glide.with(context).load(url).placeholder(placeHolder).error(errorPlaceholder).apply(RequestOptions.bitmapTransform(CircleCrop())).into(this)
}

fun View.onBackPressed() {
    onClickSafe {
        val context = this.context
        if (context is OnBackPressedDispatcherOwner) {
            context.onBackPressedDispatcher.onBackPressed()
        }
    }
}

fun View.setLeftMargin(left: Int) {
    val lp = this.layoutParams
    if (lp is ViewGroup.MarginLayoutParams) {
        lp.leftMargin = left
        this.layoutParams = lp
    }
}

fun View.setTopMargin(top: Int) {
    val lp = this.layoutParams
    if (lp is ViewGroup.MarginLayoutParams) {
        lp.topMargin = top
        this.layoutParams = lp
    }
}

fun View.setRightMargin(right: Int) {
    val lp = this.layoutParams
    if (lp is ViewGroup.MarginLayoutParams) {
        lp.rightMargin = right
        this.layoutParams = lp
    }
}

fun View.setBottomMargin(bottom: Int) {
    val lp = this.layoutParams
    if (lp is ViewGroup.MarginLayoutParams) {
        lp.bottomMargin = bottom
        this.layoutParams = lp
    }
}

fun View.setStartMargin(start: Int) {
    val lp = this.layoutParams
    if (lp is ViewGroup.MarginLayoutParams) {
        lp.marginStart = start
        this.layoutParams = lp
    }
}

fun View.setEndMargin(end: Int) {
    val lp = this.layoutParams
    if (lp is ViewGroup.MarginLayoutParams) {
        lp.marginEnd = end
        this.layoutParams = lp
    }
}

fun View.setMargin(left: Int, top: Int, right: Int, bottom: Int) {
    val lp = this.layoutParams
    if (lp is ViewGroup.MarginLayoutParams) {
        lp.setMargins(left, top, right, bottom)
        this.layoutParams = lp
    }
}

fun View.setMarginsRelative(start: Int, top: Int, end: Int, bottom: Int) {
    val lp = this.layoutParams
    if (lp is ViewGroup.MarginLayoutParams) {
        lp.marginStart = start
        lp.marginEnd = end
        lp.topMargin = top
        lp.bottomMargin = bottom
        this.layoutParams = lp
    }
}

fun View.setScale(scaleX: Float, scaleY: Float) {
    this.scaleX = scaleX
    this.scaleY = scaleY
}

fun View.setPivot(pivotX: Float, pivotY: Float) {
    this.pivotX = pivotX
    this.pivotY = pivotY
}

fun View.setWidth(width: Int) {
    val lp = this.layoutParams
    if (lp is ViewGroup.LayoutParams) {
        lp.width = width
        this.layoutParams = lp
    }
}

fun View.setHeight(height: Int) {
    val lp = this.layoutParams
    if (lp is ViewGroup.LayoutParams) {
        lp.height = height
        this.layoutParams = lp
    }
}

fun View.setWidthHeight(width: Int, height: Int) {
    val params = this.layoutParams
    if (params is ViewGroup.LayoutParams) {
        params.height = height
        params.width = width
        this.layoutParams = params
    }
}

fun View.setLayoutGravity(layoutGravity: Int) {
    val lp = this.layoutParams
    if (lp is LinearLayout.LayoutParams) {
        lp.gravity = layoutGravity
        this.layoutParams = lp
    } else if (lp is FrameLayout.LayoutParams) {
        lp.gravity = layoutGravity
    } else {
        Log.e("ViewExt", "setLayoutGravity not supported")
    }
}

fun View.animateHide(originHeight: Int, duration: Long = 100L,
                     listener: AnimatorListenerAdapter?) {
    val lp = this.layoutParams
    if (lp is ViewGroup.LayoutParams) {
        val animator = ValueAnimator.ofInt(originHeight, 0)
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = duration
        animator.addUpdateListener { animation ->
            lp.height = animation.animatedValue as Int
            this.layoutParams = lp
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                this@animateHide.visibility = View.GONE
            }
        })
        listener?.let {
            animator.addListener(listener)
        }
        animator.start()
    }
}

fun View.animateShow(targetHeight: Int, duration: Long = 100L,
                     listener: AnimatorListenerAdapter?) {
    val lp = this.layoutParams
    if (lp is ViewGroup.LayoutParams) {
        val animator = ValueAnimator.ofInt(0, targetHeight)
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = duration
        animator.addUpdateListener { animation ->
            lp.height = animation.animatedValue as Int
            this.layoutParams = lp
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                this@animateShow.visibility = View.VISIBLE
            }
        })
        listener?.let {
            animator.addListener(listener)
        }
        animator.start()
    }
}

@JvmOverloads
fun View.animateAlpha(from: Float, to: Float, duration: Long = 300L, listener: Any? = null) {
    val animator = ValueAnimator.ofFloat(from, to)
    animator.interpolator = AccelerateDecelerateInterpolator()
    animator.duration = duration
    animator.addUpdateListener { animation ->
        this.alpha = animation.animatedValue as Float
    }
    (listener as? ValueAnimator.AnimatorUpdateListener)?.let {
        animator.addUpdateListener(it)
    }
    (listener as? Animator.AnimatorListener)?.let {
        animator.addListener(it)
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        (listener as? Animator.AnimatorPauseListener)?.let {
            animator.addPauseListener(it)
        }
    }
    animator.start()
}

fun View.animateScale(fromX: Float = 0.9f, tox: Float = 1f, fromY: Float = 0.9f, toY: Float = 1f, duration: Long = 150L) {
    val mPropertyValuesHolderScaleX = PropertyValuesHolder.ofFloat("scaleX", fromX, tox)
    val mPropertyValuesHolderScaleY = PropertyValuesHolder.ofFloat("scaleY", fromY, toY)
    val animator = ValueAnimator.ofPropertyValuesHolder(mPropertyValuesHolderScaleX, mPropertyValuesHolderScaleY)
    animator.interpolator = AccelerateDecelerateInterpolator()
    animator.duration = duration
    animator.addUpdateListener { animation ->
        val animatorValueScaleX = animation.getAnimatedValue("scaleX") as Float
        val animatorValueScaleY = animation.getAnimatedValue("scaleY") as Float
        this.scaleX = animatorValueScaleX
        this.scaleY = animatorValueScaleY
    }
    animator.start()
}

fun View.animateTranslateY(fromY: Int, toY: Int, duration: Long = 300L, listener: Any? = null) {
    val animator = ValueAnimator.ofInt(fromY, toY)
    animator.interpolator = AccelerateDecelerateInterpolator()
    animator.duration = duration
    animator.addUpdateListener { animation ->
        this.scrollTo(0, animation.animatedValue as Int)
    }
    (listener as? ValueAnimator.AnimatorUpdateListener)?.let {
        animator.addUpdateListener(it)
    }
    (listener as? Animator.AnimatorListener)?.let {
        animator.addListener(it)
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        (listener as? Animator.AnimatorPauseListener)?.let {
            animator.addPauseListener(it)
        }
    }
    animator.start()
}

fun View.setRelativeRule(rule: Int, targetView: View) {
    if (this.layoutParams is RelativeLayout.LayoutParams) {
        val params = this.layoutParams as RelativeLayout.LayoutParams
        params.addRule(rule, targetView.id)
        this.layoutParams = params
    }
}

fun View.setChangeAlphaWhenPressed(pressedAlpha: Float = 0.5f) {
    this.setOnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                this.alpha = pressedAlpha
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                this.alpha = 1.0f
            }
        }
        false
    }
}

fun TextView.setTextColorID(@ColorRes color: Int) {
    this.setTextColor(ResourceUtils.getColor(color))
}

fun View.setBackgroundColorID(@ColorRes color: Int) {
    this.setBackgroundColor(ResourceUtils.getColor(color))
}

fun DialogFragment.showAllowingStateLoss(manager: FragmentManager, tag: String?) {
    manager.beginTransaction().add(this, tag).commitAllowingStateLoss()
}

fun View.setSelfRotate(duration: Long) {
    val anim = RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f)
    anim.interpolator = LinearInterpolator()
    anim.repeatCount = Animation.INFINITE
    anim.duration = duration
    this.animation = anim
    this.startAnimation(anim)
}


/**
 * 开始自转动画
 */
fun View.startSelfRotate(duration: Long) {
    val endAction = object : Runnable {
        override fun run() {
            animate().setDuration(duration)
                .rotationBy(360.0f)
                .setInterpolator(LinearInterpolator())
                .withEndAction(this)
                .start()
        }
    }

    animate().setDuration(duration)
        .rotationBy(360.0f)
        .setInterpolator(LinearInterpolator())
        .withEndAction(endAction)
        .start()
}

/**
 * 停止自转动画
 */
fun View.stopSelfRotate() {
    animate().cancel()
}


fun View.setTranslationAnim(duration: Long, fromX: Float, toX: Float,
                            fromY: Float, toY: Float, delay: Long) {
    val anim = TranslateAnimation(fromX, toX, fromY, toY)
    anim.interpolator = LinearInterpolator()
    anim.fillAfter = true
    anim.startOffset = delay
    anim.duration = duration
    this.startAnimation(anim)
}

fun TextView.enableUnderLine() {
    this.paintFlags = this.paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun View.setFadeOutAnim(duration: Long, delay: Long,
                        fadeOutListener: Animation.AnimationListener? = null) {

    val fadeOut = AlphaAnimation(1f, 0f)
    fadeOut.interpolator = LinearInterpolator()
    fadeOut.startOffset = delay
    fadeOut.duration = duration
    fadeOut.setAnimationListener(fadeOutListener)
    fadeOut.fillAfter = true
    this.startAnimation(fadeOut)
}

fun View.setFadeInAnim(duration: Long, delay: Long,
                       fadeInListener: Animation.AnimationListener? = null) {

    val fadeIn = AlphaAnimation(0f, 1f)
    fadeIn.interpolator = LinearInterpolator()
    fadeIn.startOffset = delay
    fadeIn.duration = duration
    fadeIn.setAnimationListener(fadeInListener)
    fadeIn.fillAfter = true
    this.startAnimation(fadeIn)
}

fun View.setDownOutAnim(duration: Long, delay: Long,
                        downOutListener: Animation.AnimationListener? = null) {

    val downOut = TranslateAnimation(
        TranslateAnimation.RELATIVE_TO_PARENT, 0f, TranslateAnimation.RELATIVE_TO_PARENT, 0f,
        TranslateAnimation.RELATIVE_TO_PARENT, 0f, TranslateAnimation.RELATIVE_TO_PARENT, 1.0f)
    downOut.interpolator = LinearInterpolator()
    downOut.startOffset = delay
    downOut.duration = duration
    downOut.setAnimationListener(downOutListener)
    this.startAnimation(downOut)
}

fun View.setDownInAnim(duration: Long, delay: Long,
                       downInListener: Animation.AnimationListener? = null) {

    val downIn = TranslateAnimation(
        TranslateAnimation.RELATIVE_TO_PARENT, 0f, TranslateAnimation.RELATIVE_TO_PARENT, 0f,
        TranslateAnimation.RELATIVE_TO_PARENT, 1.0f, TranslateAnimation.RELATIVE_TO_PARENT, 0f)
    downIn.interpolator = LinearInterpolator()
    downIn.startOffset = delay
    downIn.duration = duration
    downIn.setAnimationListener(downInListener)
    this.startAnimation(downIn)
}

fun RecyclerView.setDivider(context: Context, @DrawableRes resId: Int,
                            orientation: Int = DividerItemDecoration.VERTICAL) {
    val drawable = ResourceUtils.getDrawable(resId)
    drawable?.let {
        val divider = DividerItemDecoration(context, orientation)
        divider.setDrawable(drawable)
        this.addItemDecoration(divider)
    }
}

fun RecyclerView.fastSmoothScrollToPosition(targetItem: Int) {
    layoutManager?.apply {
        val maxScroll = 3
        when (this) {
            is LinearLayoutManager -> {
                val topItem = findFirstVisibleItemPosition()
                val distance = topItem - targetItem
                val anchorItem = when {
                    distance > maxScroll -> targetItem + maxScroll
                    distance < -maxScroll -> targetItem - maxScroll
                    else -> topItem
                }
                if (anchorItem != topItem) scrollToPosition(anchorItem)
                post {
                    smoothScrollToPosition(targetItem)
                }
            }
            else -> scrollToPosition(targetItem)
        }
    }
}

/**
 * view 是否被遮挡
 */
fun View.isViewCovered(): Boolean {
    val view = this
    var currentView = view
    val currentViewRect = Rect()
    val partVisible = currentView.getGlobalVisibleRect(currentViewRect)
    val totalHeightVisible = currentViewRect.bottom - currentViewRect.top >= view.measuredHeight
    val totalWidthVisible = currentViewRect.right - currentViewRect.left >= view.measuredWidth
    val totalViewVisible = partVisible && totalHeightVisible && totalWidthVisible
    if (!totalViewVisible) //if any part of the view is clipped by any of its parents,return true
        return true
    while (currentView.parent is ViewGroup) {
        val currentParent = currentView.parent as ViewGroup
        if (currentParent.visibility != View.VISIBLE) //if the parent of view is not visible,return true
            return true
        val start =  currentParent.indexOfChild(currentView)
        for (i in start + 1 until currentParent.childCount) {
            val viewRect = Rect()
            view.getGlobalVisibleRect(viewRect)
            val otherView = currentParent.getChildAt(i)
            val otherViewRect = Rect()
            otherView.getGlobalVisibleRect(otherViewRect)
            if (Rect.intersects(viewRect, otherViewRect)) //if view intersects its older brother(covered),return true
                return true
        }
        currentView = currentParent
    }
    return false
}