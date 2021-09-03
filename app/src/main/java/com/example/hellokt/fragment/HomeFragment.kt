package com.example.hellokt.fragment

import android.os.Bundle
import com.example.baseproject.base.BaseBVMFragment
import com.example.hellokt.BR
import com.example.hellokt.R
import com.example.hellokt.databinding.FragmentHomeBinding
import com.example.hellokt.viewmodel.HomeViewModel

class HomeFragment : BaseBVMFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun createViewModel(): HomeViewModel {
        return HomeViewModel()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initVariableId(): Int {
        return BR.homeVm
    }

    override fun initialize(savedInstanceState: Bundle?) {

    }

}