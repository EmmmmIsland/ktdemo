package com.example.hellokt.application;

import android.app.Application;
import android.content.Context;

import com.example.hellokt.utils.GlobalContext;

public class HelloApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        GlobalContext.Companion.setAppContext(base);
    }
}
