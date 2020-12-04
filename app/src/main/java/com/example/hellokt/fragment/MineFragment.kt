package com.example.hellokt.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hellokt.R
import com.example.hellokt.activity.SettingActivity
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : Fragment(), View.OnClickListener {

    fun getInstance() : MineFragment{
        return MineFragment().getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_mine,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_setting.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_setting -> {
                val i = Intent(context,SettingActivity().javaClass)
                startActivity(i)
            }
        }
    }

}