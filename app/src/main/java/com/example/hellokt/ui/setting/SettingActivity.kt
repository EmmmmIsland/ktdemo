package com.example.hellokt.ui.setting

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baseproject.base.BaseBVMActivity
import com.example.baseproject.router.ARouterConfig
import com.example.hellokt.BR
import com.example.hellokt.R
import com.example.hellokt.databinding.ActivitySettingBinding
import com.example.hellokt.model.User

@Route(path = ARouterConfig.AROUTER_PATH_SETTING_ACTIVITY)
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

    override fun initView() {
        val user = User("mma", 12)
        binding.user = user
    }

    override fun initData() {

    }
}