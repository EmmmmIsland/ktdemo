package com.example.hellokt.activity

import com.example.hellokt.R

class SplashActivity : BaseActivity() {

    override fun initView() {
        setContentView(R.layout.activity_splash)
        LoginActivity().start(this)
        finish()
    }

    override fun initData() {

    }
}