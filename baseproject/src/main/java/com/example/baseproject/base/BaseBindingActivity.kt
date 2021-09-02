package com.example.baseproject.base

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindingActivity<T : ViewDataBinding> : BaseActivity(){

    protected lateinit var binding: T
        private set

    override fun initContentView() {
        injectDataBinding()
    }

    protected fun injectDataBinding() {
        binding = DataBindingUtil.setContentView(this,getLayoutId())
        binding.lifecycleOwner = this
    }

    override fun onDestroy() {
        binding.unbind()
        super.onDestroy()
    }
}