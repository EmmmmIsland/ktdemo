package com.example.hellokt.application

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.example.hellokt.utils.GlobalContext

class HelloApplication : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        GlobalContext.setAppContext(base)
        //初始化路由管理
        ARouter.init(this)
    }
}