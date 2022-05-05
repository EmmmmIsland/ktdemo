package com.example.hellokt.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demo.toolkit.ext.toast
import com.example.baseproject.base.BaseViewModel

class LoginViewModel : BaseViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name
    private val _age = MutableLiveData<String>()
    val age: LiveData<String> = _age
    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> = _loginStatus

    fun login() {
        launch(
            LoginRepository(),
            success = { it ->
                toast("success")
                _name.value = it.data?.name
                _age.value = it.data?.age
                _loginStatus.value = true
            },
            error = { _, message ->
                toast("error")
            },
            complete = {
                toast("complete")
            })
    }
}