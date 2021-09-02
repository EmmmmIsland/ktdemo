package com.example.hellokt.viewmodel

import android.view.View
import com.example.baseproject.base.BaseViewModel
import com.example.hellokt.activity.MainActivity
import com.example.hellokt.utils.ToastUtil

class LoginViewModel : BaseViewModel() {
    fun onClickLogin(v: View){
        MainActivity().start(v.context)
    }

}