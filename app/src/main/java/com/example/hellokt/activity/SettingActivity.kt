package com.example.hellokt.activity

import android.os.Bundle
import com.example.baseproject.base.BaseBVMActivity
import com.example.hellokt.BR
import com.example.hellokt.R
import com.example.hellokt.databinding.ActivitySettingBinding
import com.example.hellokt.model.User
import com.example.hellokt.viewmodel.SettingViewModel

class SettingActivity : BaseBVMActivity<ActivitySettingBinding, SettingViewModel>() {

    override fun createViewModel(): SettingViewModel {
        return SettingViewModel()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun initVariableId(): Int {
        return BR.settingVm
    }

    override fun initialize(savedInstanceState: Bundle?) {
        initView()
        initData()
    }

    fun initView() {

        val user = User("mma", 12)
        binding.user = user
    }

    fun initData() {

    }


}