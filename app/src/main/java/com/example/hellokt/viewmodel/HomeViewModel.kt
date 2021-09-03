package com.example.hellokt.viewmodel

import android.view.View
import android.widget.Toast
import com.example.baseproject.base.BaseViewModel

class HomeViewModel : BaseViewModel() {

    fun btnClick(v:View){
        Toast.makeText(v.context, "haha", Toast.LENGTH_LONG).show()
    }
}