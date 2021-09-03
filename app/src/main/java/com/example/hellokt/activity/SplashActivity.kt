package com.example.hellokt.activity

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.example.baseproject.base.BaseBVMActivity
import com.example.baseproject.router.ARouterConfig
import com.example.hellokt.R
import com.example.hellokt.databinding.ActivitySplashBinding
import com.example.hellokt.viewmodel.SplashViewModel

class SplashActivity : BaseBVMActivity<ActivitySplashBinding, SplashViewModel>() {
    override fun createViewModel(): SplashViewModel {
        return SplashViewModel()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initVariableId(): Int {
        return 0
    }

    override fun initialize(savedInstanceState: Bundle?) {
        initView()
        initData()
    }

    fun initView() {
        ARouter.getInstance().build(ARouterConfig.AROUTER_PATH_LOGIN_ACTIVITY).navigation()
        finish()
    }

    fun initData() {

    }


}