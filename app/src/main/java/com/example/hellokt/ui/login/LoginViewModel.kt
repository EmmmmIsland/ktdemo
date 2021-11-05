package com.example.hellokt.ui.login

import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.demo.toolkit.ext.toast
import com.example.baseproject.base.BaseViewModel
import com.example.baseproject.router.ARouterConfig

class LoginViewModel : BaseViewModel() {
    fun onClickLogin(v: View){
        ARouter.getInstance().build(ARouterConfig.AROUTER_PATH_MAIN_ACTIVITY).navigation()
    }

}