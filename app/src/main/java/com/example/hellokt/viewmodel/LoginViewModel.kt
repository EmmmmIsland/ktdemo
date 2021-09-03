package com.example.hellokt.viewmodel

import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.example.baseproject.base.BaseViewModel
import com.example.baseproject.router.ARouterConfig
import com.example.hellokt.activity.MainActivity
import com.example.hellokt.utils.ToastUtil

class LoginViewModel : BaseViewModel() {
    fun onClickLogin(v: View){
        ARouter.getInstance().build(ARouterConfig.AROUTER_PATH_MAIN_ACTIVITY).navigation()
    }

}