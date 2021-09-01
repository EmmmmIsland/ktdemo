package com.example.hellokt.activity

import android.os.Bundle
import com.example.hellokt.R
import com.example.hellokt.base.BaseAppBVMActivity
import com.example.hellokt.databinding.ActivitySettingBinding
import com.example.hellokt.model.User
import com.example.hellokt.viewmodel.SettingViewModel

class SettingActivity : BaseAppBVMActivity<ActivitySettingBinding, SettingViewModel>() {

    override fun createViewModel(): SettingViewModel {
        return SettingViewModel()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun bindViewModel() {
        binding.settingVm = SettingViewModel()
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