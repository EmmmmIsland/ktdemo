package com.example.hellokt.application

import android.app.Application
import android.content.Context
import com.example.hellokt.utils.GlobalContext

class HelloApplication : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        GlobalContext.setAppContext(base)
    }
}