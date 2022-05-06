package com.example.hellokt.ui.navmain

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baseproject.base.BaseVmActivity
import com.example.baseproject.router.ARouterConfig
import com.example.hellokt.databinding.ActivityNavmainBinding

@Route(path = ARouterConfig.AROUTER_PATH_NAV_MAIN_ACTIVITY)
class NavMainActivity : BaseVmActivity<ActivityNavmainBinding>(){
    override fun getLayout(): ActivityNavmainBinding = ActivityNavmainBinding.inflate(layoutInflater)

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }
}