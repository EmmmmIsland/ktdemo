package com.example.hellokt.fragment

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.example.baseproject.base.BaseBVMFragment
import com.example.baseproject.router.ARouterConfig.Companion.AROUTER_PATH_SETTING_ACTIVITY
import com.example.hellokt.BR
import com.example.hellokt.R
import com.example.hellokt.databinding.FragmentMineBinding
import com.example.hellokt.viewmodel.MineViewModel

class MineFragment : BaseBVMFragment<FragmentMineBinding, MineViewModel>() {

    override fun createViewModel(): MineViewModel {
        return MineViewModel()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initVariableId(): Int {
        return BR.mineVm
    }

    override fun initialize(savedInstanceState: Bundle?) {

    }


}