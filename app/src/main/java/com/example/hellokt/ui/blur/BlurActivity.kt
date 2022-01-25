package com.example.hellokt.ui.blur

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baseproject.base.BaseVmActivity
import com.example.baseproject.router.ARouterConfig
import com.example.hellokt.databinding.ActivityBlurBinding

@Route(path = ARouterConfig.AROUTER_PATH_BLUR_ACTIVITY)
class BlurActivity : BaseVmActivity<ActivityBlurBinding>() {

    override fun getLayout(): ActivityBlurBinding  = ActivityBlurBinding.inflate(layoutInflater)

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        binding.toolBar.setNavigationOnClickListener { finish() }
    }

    override fun initData() {

    }


}