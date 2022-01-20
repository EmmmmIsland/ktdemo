package com.example.hellokt.ui.splash

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.example.baseproject.base.BaseVmActivity
import com.example.baseproject.router.ARouterConfig
import com.example.hellokt.databinding.ActivitySplashBinding

class SplashActivity : BaseVmActivity<ActivitySplashBinding>() {
    override fun getLayout(): ActivitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        ARouter.getInstance().build(ARouterConfig.AROUTER_PATH_LOGIN_ACTIVITY).navigation()
        finish()
    }

    override fun initData() {

    }
}