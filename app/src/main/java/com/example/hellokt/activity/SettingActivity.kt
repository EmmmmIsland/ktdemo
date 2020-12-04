package com.example.hellokt.activity

import androidx.databinding.DataBindingUtil
import com.example.hellokt.R
import com.example.hellokt.databinding.ActivitySettingBinding
import com.example.hellokt.model.User
import com.example.hellokt.viewmodel.SettingViewModel


class SettingActivity : BaseActivity() {
    override fun initView() {
        val binding =
            DataBindingUtil.setContentView<ActivitySettingBinding>(this, R.layout.activity_setting)
        binding.settingVm = SettingViewModel()
        val user = User("mma", 12)
        binding.user = user
    }

    override fun initData() {

    }
}