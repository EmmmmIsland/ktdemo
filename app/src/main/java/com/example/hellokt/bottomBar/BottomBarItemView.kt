package com.example.hellokt.bottomBar

import android.annotation.SuppressLint
import android.content.Context
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat
import com.example.hellokt.R
import kotlinx.android.synthetic.main.bottom_bar_item.view.*

class BottomBarItemView constructor(
    context: Context,
    attrs: String,
    DrawableRes: Int,
) :
    FrameLayout(context, null, 0) {

    init {
        inflate(context, R.layout.bottom_bar_item, this)
        setItemNameKt(attrs)
        setItemIcon(DrawableRes)
    }

    private fun setItemNameKt(name: String?) {
        item_name.text = name
    }

    private fun setItemIcon(drawable: Int) {
        item_icon.setImageDrawable(ResourcesCompat.getDrawable(resources,drawable,null));
    }

    override fun setSelected(selected: Boolean) {
        item_icon.isSelected = selected
        item_name.isSelected = selected
    }
}