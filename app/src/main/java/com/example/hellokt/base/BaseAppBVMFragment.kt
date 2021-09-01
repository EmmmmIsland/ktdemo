package com.example.hellokt.base

import androidx.databinding.ViewDataBinding

abstract class BaseAppBVMFragment<B : ViewDataBinding, VM : BaseViewModel> :
    BaseBVMFragment<B, VM>() {

    override fun showLoadingView(isShow: Boolean) {

    }

    override fun showEmptyView(isShow: Boolean) {

    }

    override fun navigate(page: Any) {

    }

    override fun backPress(arg: Any?) {

    }

    override fun finishPage(arg: Any?) {

    }

}