package com.example.hellokt.bottomBar

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.util.*

class BottomBarLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(
        context, attrs, defStyleAttr
    ), BottomBarController {
    private var items: MutableList<BottomBarItemView>? = ArrayList()
    private var fragments: MutableList<Fragment>? = ArrayList()
    private var sourceFrameLayout = 0
    private var activity: FragmentActivity? = null

    override fun setSourceFrameLayout(
        activity: FragmentActivity?,
        frameLayoutId: Int,
    ): BottomBarLayout {
        sourceFrameLayout = frameLayoutId
        this.activity = activity
        return this
    }


    /**
     * 添加Item
     *
     * @param view
     */
    override fun addItemView(view: BottomBarItemView?, fragment: Fragment?): BottomBarLayout? {
        if (view != null) {
            items!!.add(view)
        }
        if (fragment != null) {
            fragments!!.add(fragment)
        }
        return this
    }

    /**
     * 根据position 移除指定的View
     *
     * @param position
     * @return
     */
    override fun removeItemView(position: Int): Boolean {
        val childCount = childCount
        if (childCount <= 0) return false
        val removeView: View?
        return try {
            removeView = getChildAt(position)
            if (removeView == null) return false
            removeView(removeView)
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * 移除指定的View
     *
     * @param view
     * @return
     */
    override fun removeItemView(view: BottomBarItemView?): Boolean {
        return if (view == null) false else try {
            removeView(view)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun setSelected(position: Int) {
        if (items != null && items!!.size > 0) for (i in items!!.indices) {
            if (position == i) {
                items!![i].isSelected = true
                activity!!.supportFragmentManager.beginTransaction().show(fragments!![i]).commit()
            } else {
                items!![i].isSelected = false
                activity!!.supportFragmentManager.beginTransaction().hide(fragments!![i]).commit()
            }
        }
    }

    override fun initialise() {
        for (i in items!!.indices) {
            addView(items!![i])
            val lp = items!![i].layoutParams as LayoutParams
            lp.weight = 1f
            lp.height = LayoutParams.MATCH_PARENT
            lp.width = LayoutParams.MATCH_PARENT
            lp.gravity = Gravity.CENTER
            items!![i].layoutParams = lp
            items!![i].setOnClickListener { setSelected(i) }
            activity!!.supportFragmentManager.beginTransaction().add(sourceFrameLayout,
                fragments!![i]).commit()
        }
    }
}
