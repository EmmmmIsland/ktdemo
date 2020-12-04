package com.example.hellokt.utils

import android.app.Application
import android.content.Context

class GlobalContext {

    private fun GlobalContext() {
    }

    companion object {
        private var application: Application? = null

        fun setApplication(application: Application) {
            this.application = application
        }

        fun getApplication(): Application {
            return application!!
        }

        private var appContext: Context? = null

        fun setAppContext(appContext: Context) {
            this.appContext = appContext
        }

        fun getAppContext(): Context {
            return appContext!!
        }

    }
}