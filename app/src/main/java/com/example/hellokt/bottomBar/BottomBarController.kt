package com.example.hellokt.bottomBar

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

interface BottomBarController {

    fun setSourceFrameLayout(activity: FragmentActivity?, @IdRes frameLayoutId: Int): BottomBarLayout?

    fun addItemView(view: BottomBarItemView?, fragment: Fragment?): BottomBarLayout?

    fun removeItemView(position: Int): Boolean

    fun removeItemView(view: BottomBarItemView?): Boolean

    fun setSelected(position: Int)

    fun initialise()
}