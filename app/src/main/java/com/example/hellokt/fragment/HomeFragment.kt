package com.example.hellokt.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.hellokt.R
import com.example.baseproject.base.BaseAppBVMFragment
import com.example.hellokt.databinding.FragmentHomeBinding
import com.example.hellokt.viewmodel.HomeViewModel

class HomeFragment : BaseAppBVMFragment<FragmentHomeBinding, HomeViewModel>(), View.OnClickListener {
    var hh = "hello"

    override fun createViewModel(): HomeViewModel {
        return HomeViewModel()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initialize(savedInstanceState: Bundle?) {
        initData()
    }

    fun initData() {
        val cc = view?.findViewById<TextView>(R.id.tv_gg);
        cc?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_gg -> {
                Toast.makeText(context, hh, Toast.LENGTH_LONG).show()
            }
        }
    }
}