package com.example.hellokt.ui.setting

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baseproject.base.BaseVmActivity
import com.example.baseproject.router.ARouterConfig
import com.example.hellokt.databinding.ActivitySettingBinding

@Route(path = ARouterConfig.AROUTER_PATH_SETTING_ACTIVITY)
class SettingActivity : BaseVmActivity<ActivitySettingBinding>() {

    override fun getLayout(): ActivitySettingBinding = ActivitySettingBinding.inflate(layoutInflater)

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }

    override fun initData() {

    }


}