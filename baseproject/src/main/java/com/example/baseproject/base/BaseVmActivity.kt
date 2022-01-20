package com.example.baseproject.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding

abstract class BaseVmActivity<T : ViewBinding>() : BaseActivity() {

    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivity(savedInstanceState)
    }

    open fun initActivity(savedInstanceState: Bundle?) {
        binding = getLayout()
        setContentView(binding.root)
        initView(savedInstanceState)
        initViewModel()
        initData()
    }

    abstract fun getLayout(): T

    open fun initView(savedInstanceState: Bundle?) {

    }

    @CallSuper
    open fun initViewModel() {

    }

    open fun initData() {}

    open fun initTitle(title: String) {}

}