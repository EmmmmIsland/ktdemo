package com.example.hellokt.ui.navmain

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baseproject.base.BaseVmActivity
import com.example.baseproject.router.ARouterConfig
import com.example.hellokt.R
import com.example.hellokt.databinding.ActivityNavmainBinding

@Route(path = ARouterConfig.AROUTER_PATH_NAV_MAIN_ACTIVITY)
class NavMainActivity : BaseVmActivity<ActivityNavmainBinding>(){
    override fun getLayout(): ActivityNavmainBinding = ActivityNavmainBinding.inflate(layoutInflater)

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        binding.navView.itemIconTintList = null

        val navController = Navigation.findNavController(this, R.id.my_nav_host_fragment)
        binding.navView.setupWithNavController(navController)

//        binding.navView.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.home -> {
//                    Navigation.findNavController(this,R.id.my_nav_host_fragment).navigate(R.id.navHomeFragment)
//                    return@setOnItemSelectedListener true
//                }
//                R.id.mid -> {
//                    Navigation.findNavController(this,R.id.my_nav_host_fragment).navigate(R.id.navMidFragment)
//
//                    return@setOnItemSelectedListener true
//                }
//                R.id.mine -> {
//                    Navigation.findNavController(this,R.id.my_nav_host_fragment).navigate(R.id.navMineFragment)
//
//                    return@setOnItemSelectedListener true
//                }
//            }
//            return@setOnItemSelectedListener false
//        }
    }
}