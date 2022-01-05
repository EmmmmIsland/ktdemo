package com.example.hellokt.ui.login

import android.content.Context
import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.demo.toolkit.ext.onClickSafe
import com.example.baseproject.base.BaseBVMActivity
import com.example.baseproject.router.ARouterConfig
import com.example.hellokt.BR
import com.example.hellokt.R
import com.example.hellokt.databinding.ActivityLoginBinding

@Route(path = ARouterConfig.AROUTER_PATH_LOGIN_ACTIVITY)
class LoginActivity : BaseBVMActivity<ActivityLoginBinding, LoginViewModel>() {
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

    override fun initVariableId(): Int {
        return BR.loginVm
    }

    override fun initView() {
        binding.loginByPhone.onClickSafe {
            ARouter.getInstance().build(ARouterConfig.AROUTER_PATH_MAIN_ACTIVITY).navigation()
        }
    }

    override fun initData() {

    }
}