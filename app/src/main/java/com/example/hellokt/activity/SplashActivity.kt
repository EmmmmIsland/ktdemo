package com.example.hellokt.activity

import android.os.Bundle
import com.example.hellokt.R
import com.example.baseproject.base.BaseAppBVMActivity
import com.example.hellokt.databinding.ActivitySplashBinding
import com.example.hellokt.viewmodel.SplashViewModel

class SplashActivity : BaseAppBVMActivity<ActivitySplashBinding, SplashViewModel>() {
    override fun createViewModel(): SplashViewModel {
        return SplashViewModel()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun bindViewModel() {

    }

    override fun initialize(savedInstanceState: Bundle?) {
        initView()
        initData()
    }

    fun initView() {
        LoginActivity().start(this)
        finish()
    }

    fun initData() {

    }


}