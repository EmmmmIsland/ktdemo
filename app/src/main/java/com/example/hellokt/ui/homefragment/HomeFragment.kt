package com.example.hellokt.ui.homefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.demo.toolkit.ext.onClickSafe
import com.example.baseproject.base.BaseFragment
import com.example.baseproject.router.ARouterConfig
import com.example.hellokt.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun getLayout(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        binding.tvConstraintLayout.onClickSafe {
            ARouter.getInstance().build(ARouterConfig.AROUTER_PATH_CONSTRAINT_LAYOUT_ACTIVITY).navigation()
        }
    }
}