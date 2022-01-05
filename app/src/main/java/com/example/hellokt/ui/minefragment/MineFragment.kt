package com.example.hellokt.ui.minefragment

import android.os.Bundle
import com.example.baseproject.base.BaseBVMFragment
import com.example.hellokt.BR
import com.example.hellokt.R
import com.example.hellokt.databinding.FragmentMineBinding

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



}