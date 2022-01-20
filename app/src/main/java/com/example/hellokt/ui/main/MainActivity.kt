package com.example.hellokt.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baseproject.base.BaseVmActivity
import com.example.baseproject.router.ARouterConfig
import com.example.hellokt.R
import com.example.hellokt.bottomBar.BottomBarItemView
import com.example.hellokt.databinding.ActivityMainBinding
import com.example.hellokt.ui.homefragment.HomeFragment
import com.example.hellokt.ui.minefragment.MineFragment
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = ARouterConfig.AROUTER_PATH_MAIN_ACTIVITY)
class MainActivity : BaseVmActivity<ActivityMainBinding>(), View.OnClickListener {

    fun start(context: Context) {
        var intent = Intent(context, MainActivity().javaClass)
        context.startActivity(intent)
    }

    override fun getLayout(): ActivityMainBinding  = ActivityMainBinding.inflate(layoutInflater)

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
//        bottom_bar.setSourceFrameLayout(this, R.id.base_content_view)
//            ?.addItemView(BottomBarItemView(this, "主页", R.drawable.home_page_selector),
//                HomeFragment().javaClass.newInstance())
//            ?.addItemView(BottomBarItemView(this, "我的", R.drawable.mine_page_selector),
//                MineFragment().javaClass.newInstance())
//            ?.initialise()
//
//        bottom_bar.setSelected(0)
    }

    override fun initData() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
//            R.id.tv_name -> {
//                Toast.makeText(applicationContext, (a + b).toString(), Toast.LENGTH_LONG).show();
//
//            }
        }
    }

}