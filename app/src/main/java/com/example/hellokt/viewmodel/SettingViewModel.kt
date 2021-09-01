package com.example.hellokt.viewmodel

import com.example.hellokt.base.BaseViewModel
import com.example.hellokt.utils.ToastUtil

class SettingViewModel :  BaseViewModel() {
    fun settingBtnOnclick() {
        ToastUtil.showToast("mmad")
    }
}