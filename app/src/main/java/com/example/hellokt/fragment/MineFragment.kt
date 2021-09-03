package com.example.hellokt.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.example.baseproject.base.BaseBVMFragment
import com.example.baseproject.router.ARouterConfig.Companion.AROUTER_PATH_SETTING_ACTIVITY
import com.example.hellokt.R
import com.example.hellokt.activity.SettingActivity
import com.example.hellokt.databinding.FragmentMineBinding
import com.example.hellokt.viewmodel.MineViewModel

class MineFragment : BaseBVMFragment<FragmentMineBinding, MineViewModel>(), View.OnClickListener {

    override fun createViewModel(): MineViewModel {
        return MineViewModel()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initialize(savedInstanceState: Bundle?) {
        initData()
    }

    fun initData() {
        binding.tvSetting.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_setting -> {
                ARouter.getInstance().build(AROUTER_PATH_SETTING_ACTIVITY).navigation()
            }
        }
    }
}