package com.example.hellokt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.hellokt.bottomBar.BottomBarItemView
import com.example.hellokt.bottomBar.BottomBarLayout
import com.example.hellokt.fragment.HomeFragment
import com.example.hellokt.fragment.MineFragment

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val a: Int = 2;
    private val b = 9;

    fun start(context: Context) {
//        var intent = Intent(context, MainActivity::class.java)
        var intent = Intent(context, MainActivity().javaClass)
        context.startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val frameLayout = findViewById<FrameLayout>(R.id.base_content_view)
        val bottomBar = findViewById<BottomBarLayout>(R.id.bottom_bar)
        bottomBar.setSourceFrameLayout(this, R.id.base_content_view)
            .addItemView(BottomBarItemView(this, "主页", R.drawable.home_page_selector),
                HomeFragment().javaClass.newInstance())
            .addItemView(BottomBarItemView(this, "我的", R.drawable.mine_page_selector),
                MineFragment().javaClass.newInstance())
            .initialise()

        bottomBar.setSelected(0)

//            .addItemView(new BottomBarItemView(mContext, mContext.getResources().getString(R.string.home_page), R.drawable.home_page_selector), chatRoomModuleFragment)
//        .addItemView(new BottomBarItemView(mContext, mContext.getResources().getString(R.string.mine_page), R.drawable.mine_page_selector), mineModuleFragment)
//        .initialise();

//        val cc = findViewById<TextView>(R.id.tv_name);
//        cc.setOnClickListener(this);
//        Toast.makeText(applicationContext, "hello", Toast.LENGTH_LONG).show();
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