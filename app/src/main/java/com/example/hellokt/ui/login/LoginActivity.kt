package com.example.hellokt.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.demo.toolkit.ext.onClickSafe
import com.demo.toolkit.ext.toast
import com.example.baseproject.base.BaseVmActivity
import com.example.baseproject.router.ARouterConfig
import com.example.hellokt.databinding.ActivityLoginBinding

@Route(path = ARouterConfig.AROUTER_PATH_LOGIN_ACTIVITY)
class LoginActivity : BaseVmActivity<ActivityLoginBinding>() {
    private lateinit var viewModel: LoginViewModel
    fun start(context: Context) {
        val i = Intent(context, LoginActivity().javaClass)
        context.startActivity(i)
    }

    override fun getLayout(): ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.loginByPhone.onClickSafe {
            viewModel.login()
//            ARouter.getInstance().build(ARouterConfig.AROUTER_PATH_MAIN_ACTIVITY).navigation()
        }
        binding.btnNavigation.onClickSafe {
            ARouter.getInstance().build(ARouterConfig.AROUTER_PATH_NAV_MAIN_ACTIVITY).navigation()
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.loginStatus.observe(this, {
            if (it) {
                toast("name:" + viewModel.name.value + " age" + viewModel.age.value)
                ARouter.getInstance().build(ARouterConfig.AROUTER_PATH_MAIN_ACTIVITY).navigation()
            }
        })
    }

    override fun initData() {

    }


}