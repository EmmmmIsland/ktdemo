package com.demo.toolkit

import android.app.Application
import android.util.Log
import androidx.annotation.Keep
import com.demo.toolkit.App
import java.lang.Exception

@Keep
object App {
    @Keep
    lateinit var INSTANCE: Application  //= AppGlobals.getInitialApplication();


    init {
        var app: Application? = null
        try {
            app =
                Class.forName("android.app.AppGlobals").getMethod("getInitialApplication")
                    .invoke(null) as Application
            checkNotNull(app) { "Static initialization of Applications must be on main thread." }
        } catch (e: Exception) {
            Log.e("", "Failed to get current application from AppGlobals." + e.message)
            try {
                app =
                    Class.forName("android.app.ActivityThread").getMethod("currentApplication")
                        .invoke(null) as Application
            } catch (ex: Exception) {
                Log.e("", "Failed to get current application from ActivityThread." + e.message)
            }
        } finally {
            if (app != null) {
                INSTANCE = app
            }
        }
    }
}