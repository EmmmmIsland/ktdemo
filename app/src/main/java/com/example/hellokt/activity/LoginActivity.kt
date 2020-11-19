package com.example.hellokt.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.example.hellokt.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener {
    fun start(context: Context) {
        val i = Intent(context, LoginActivity().javaClass)
        context.startActivity(i)
    }

    override fun initView() {
        setContentView(R.layout.activity_login)
        login_by_phone.setOnClickListener(this)
    }

    override fun initData() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login_by_phone -> {
                MainActivity().start(this)
            }
        }
    }
}