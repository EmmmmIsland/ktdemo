package com.example.hellokt.base

import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindingActivity<B : ViewDataBinding> : BaseActivity(),
    ViewBehavior {

    protected lateinit var binding: B
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

    override fun navigate(page: Any) {
        startActivity(Intent(this, page as Class<*>))
    }

    override fun backPress(arg: Any?) {
        onBackPressed()
    }

    override fun finishPage(arg: Any?) {
        finish()
    }
}