package com.example.baseproject.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView()
        initialize()
    }

    protected open fun initContentView() {
        setContentView(getLayoutId())
    }

    protected abstract @LayoutRes
    fun getLayoutId(): Int

    /**
     *  初始化操作
     */
    fun initialize(){
        initView()
        initData()
    }

    open fun initView(){}
    open fun initData(){}
}