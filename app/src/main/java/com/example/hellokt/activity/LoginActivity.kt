package com.example.hellokt.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.hellokt.R
import com.example.baseproject.base.BaseAppBVMActivity
import com.example.hellokt.databinding.ActivityLoginBinding
import com.example.hellokt.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseAppBVMActivity<ActivityLoginBinding, LoginViewModel>(),
    View.OnClickListener {
    fun start(context: Context) {
        val i = Intent(context, LoginActivity().javaClass)
        context.startActivity(i)
    }

    override fun createViewModel(): LoginViewModel {
        return LoginViewModel()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun bindViewModel() {

    }

    override fun initialize(savedInstanceState: Bundle?) {
        initView()
        initData()
    }

    fun initView() {
        login_by_phone.setOnClickListener(this)
    }

    fun initData() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login_by_phone -> {
                MainActivity().start(this)
            }
        }
    }




}