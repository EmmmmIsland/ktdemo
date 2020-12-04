package com.example.hellokt.viewmodel

import androidx.databinding.BaseObservable
import com.example.hellokt.utils.ToastUtil

class SettingViewModel : BaseObservable() {
    fun settingBtnOnclick() {
        ToastUtil.showToast("mmad")
    }
}