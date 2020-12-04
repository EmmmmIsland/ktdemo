package com.example.hellokt.activity

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.FrameLayout
import com.example.hellokt.R
import com.example.hellokt.bottomBar.BottomBarItemView
import com.example.hellokt.bottomBar.BottomBarLayout
import com.example.hellokt.fragment.HomeFragment
import com.example.hellokt.fragment.MineFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), View.OnClickListener {
    private val a: Int = 2
    private val b = 9

    fun start(context: Context) {
        var intent = Intent(context, MainActivity().javaClass)
        context.startActivity(intent)
    }

    override fun initView() {
        setContentView(R.layout.activity_main)
        val frameLayout = findViewById<FrameLayout>(R.id.base_content_view)

        bottom_bar.setSourceFrameLayout(this, R.id.base_content_view)
            ?.addItemView(BottomBarItemView(this, "主页", R.drawable.home_page_selector),
                HomeFragment().javaClass.newInstance())
            ?.addItemView(BottomBarItemView(this, "我的", R.drawable.mine_page_selector),
                MineFragment().javaClass.newInstance())
            ?.initialise()

        bottom_bar.setSelected(0)

        //        val cc = findViewById<TextView>(R.id.tv_name);
        //        cc.setOnClickListener(this);
        //        Toast.makeText(applicationContext, "hello", Toast.LENGTH_LONG).show();
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