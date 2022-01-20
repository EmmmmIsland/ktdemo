package com.example.baseproject.base

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.ActivityManager
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception

/**
 * 对Activity的基本封装
 *
 *  1. 提供对Activity生命周期的默认输出
 *  1. 提供简便的获取Activity当前状态的方法
 *  1. 提供侦测Activity启动来源的功能
 *
 *
 */
abstract class BaseActivity : AppCompatActivity() {
    protected val TAG = javaClass.simpleName

    /**
     * Activity是否已经Create
     */
    var isActivityCreated = false
        private set

    /**
     * Activity是否已经Start
     */
    var isActivityStarted = false
        private set

    /**
     * Activity是否已经Resumed
     */
    var isActivityResumed = false
        private set
    private var mResumedCount = 0

    /**
     * 获取Activity restart的次数
     */
    var restarted = 0
        private set
    private var mCreatedTime: Long = 0
    private var mIsRestoredToTop = false
    var source: String? = null
    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        mCreatedTime = SystemClock.elapsedRealtime()
        super.onCreate(savedInstanceState)
        setTranslucentStatus()
        isActivityCreated = true
        if (isFullScreen) {
            // 输入法弹出时  fullscreen沉浸模式会失效
            window.decorView.setOnSystemUiVisibilityChangeListener { visibility: Int -> recheckTranslucent() }
        }
    }

    override fun setRequestedOrientation(requestedOrientation: Int) {
        super.setRequestedOrientation(requestedOrientation)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.flags or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT > 0) {
            mIsRestoredToTop = true
        }
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        isActivityStarted = true
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        isActivityResumed = true
        mResumedCount++
    }

    override fun onPostResume() {
        super.onPostResume()
        // 8.0以下时，如果Activity首次创建时间超过10秒则直接结束，否则将产生BadTokenException
        if (mResumedCount == 1 && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val cost = SystemClock.elapsedRealtime() - mCreatedTime
            if (cost > 10 * 1000) {
                finish()
            }
        }
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        isActivityResumed = false
    }

    @CallSuper
    override fun onStop() {
        isActivityStarted = false
        super.onStop()
    }

    @CallSuper
    override fun onDestroy() {
        isActivityCreated = false
        super.onDestroy()
    }

    @CallSuper
    override fun onRestart() {
        super.onRestart()
        restarted++
    }

    @SuppressLint("MissingPermission")
    override fun finish() {
        super.finish()
        // 解决某些Android版本(4.4, 7.0, 7.1) 启动Activity 使用 FLAG_ACTIVITY_REORDER_TO_FRONT
        // 导致回退栈 错误回到Launcher界面的问题
        // 参考 https://issuetracker.google.com/issues/36986021
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT || Build.VERSION.SDK_INT == Build.VERSION_CODES.N || Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1 && mIsRestoredToTop && !isTaskRoot) {
            val tasksManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
            if (tasksManager != null) {
                try {
                    tasksManager.moveTaskToFront(
                        taskId,
                        ActivityManager.MOVE_TASK_NO_USER_ACTION
                    )
                } catch (e: Exception) {
                    // ignore
                }
            }
        }
    }

    /**
     * 是否启用沉浸式通知栏
     *
     */
    @TargetApi(19)
    fun setTranslucentStatus() {
        if (isFullScreen) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            } else {
                if (isHideNavigation) {
                    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
                } else {
                    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
                }
            }
            return
        }
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    protected val isFullScreen: Boolean
        protected get() = false
    protected val isHideNavigation: Boolean
        protected get() = false

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        recheckTranslucent()
    }

    private fun recheckTranslucent() {
        if (isFullScreen && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus()
        }
    }

    /**
     * Activity是否已经Paused
     */
    val isActivityPaused: Boolean
        get() = !isActivityResumed

    /**
     * Activity是否已经Stopped
     */
    val isActivityStopped: Boolean
        get() = !isActivityStarted
}