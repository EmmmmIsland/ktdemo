package com.example.hellokt.ui.navmain

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alibaba.android.arouter.launcher.ARouter
import com.demo.toolkit.ext.onClickSafe
import com.example.baseproject.base.BaseFragment
import com.example.baseproject.router.ARouterConfig
import com.example.hellokt.R
import com.example.hellokt.databinding.FragmentHomeBinding
import com.example.hellokt.databinding.FragmentNavHomeBinding
import com.example.hellokt.databinding.FragmentNavMidBinding
import io.reactivex.Observable
import io.reactivex.functions.Consumer

class NavMidFragment : BaseFragment<FragmentNavMidBinding>() {
    override fun getLayout(inflater: LayoutInflater, container: ViewGroup?): FragmentNavMidBinding = FragmentNavMidBinding.inflate(layoutInflater)

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        binding.tvMid.onClickSafe {
            findNavController().navigate(R.id.navMineFragment)
        }
    }
}