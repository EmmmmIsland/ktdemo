package com.example.hellokt.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding

abstract class BaseAppBVMActivity<B : ViewDataBinding, VM : BaseViewModel> :
    BaseBVMActivity<B, VM>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        SystemUIUtils.transparentStatusBar(this)
    }

    override fun showLoadingView(isShow: Boolean) {

    }

    override fun showEmptyView(isShow: Boolean) {

    }
}