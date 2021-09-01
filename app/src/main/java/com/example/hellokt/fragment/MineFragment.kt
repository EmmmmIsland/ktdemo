package com.example.hellokt.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.hellokt.R
import com.example.hellokt.activity.SettingActivity
import com.example.hellokt.base.BaseAppBVMFragment
import com.example.hellokt.databinding.FragmentMineBinding
import com.example.hellokt.viewmodel.MineViewModel

class MineFragment : BaseAppBVMFragment<FragmentMineBinding, MineViewModel>(), View.OnClickListener {

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
                val i = Intent(context, SettingActivity().javaClass)
                startActivity(i)
            }
        }
    }
}