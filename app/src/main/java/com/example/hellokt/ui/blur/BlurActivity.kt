package com.example.hellokt.ui.blur

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baseproject.base.BaseVmActivity
import com.example.baseproject.router.ARouterConfig
import com.example.hellokt.R
import com.example.hellokt.bottomBar.BottomBarItemView
import com.example.hellokt.databinding.ActivityBlurBinding
import com.example.hellokt.databinding.ActivityMainBinding
import com.example.hellokt.ui.homefragment.HomeFragment
import com.example.hellokt.ui.minefragment.MineFragment
import kotlinx.android.synthetic.main.activity_main.*

class BlurActivity : BaseVmActivity<ActivityBlurBinding>() {

    fun start(context: Context) {
        var intent = Intent(context, BlurActivity().javaClass)
        context.startActivity(intent)
    }

    override fun getLayout(): ActivityBlurBinding  = ActivityBlurBinding.inflate(layoutInflater)

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

    }

    override fun initData() {

    }


}