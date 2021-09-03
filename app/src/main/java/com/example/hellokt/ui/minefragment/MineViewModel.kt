package com.example.hellokt.ui.minefragment

import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.example.baseproject.base.BaseViewModel
import com.example.baseproject.router.ARouterConfig

class MineViewModel : BaseViewModel() {

    fun routerSetting(v:View){
        ARouter.getInstance().build(ARouterConfig.AROUTER_PATH_SETTING_ACTIVITY).navigation()
    }
}