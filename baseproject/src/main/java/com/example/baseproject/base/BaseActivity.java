package com.example.baseproject.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.CallSuper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

/**
 * 对Activity的基本封装
 * <ol>
 * <li>提供对Activity生命周期的默认输出</li>
 * <li>提供简便的获取Activity当前状态的方法</li>
 * <li>提供侦测Activity启动来源的功能</li>
 * </ol>
 *
 */
@SuppressWarnings("unused")
public abstract class BaseActivity extends AppCompatActivity {
    protected final String TAG = getClass().getSimpleName();


    private boolean mCreated;
    private boolean mStarted;
    private boolean mResumed;

    private int mResumedCount = 0;

    private int mRestarted;

    private long mCreatedTime;
    private boolean mIsRestoredToTop;

    public String source;

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {

        mCreatedTime = SystemClock.elapsedRealtime();

        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        mCreated = true;

        if (isFullScreen()) {
            // 输入法弹出时  fullscreen沉浸模式会失效
            getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(visibility -> recheckTranslucent());
        }
    }

    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        super.setRequestedOrientation(requestedOrientation);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if ((intent.getFlags() | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT) > 0) {
            mIsRestoredToTop  = true;
        }
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        mStarted = true;

    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        mResumed = true;
        mResumedCount++;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        // 8.0以下时，如果Activity首次创建时间超过10秒则直接结束，否则将产生BadTokenException
        if (mResumedCount == 1 && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            long cost = SystemClock.elapsedRealtime() - mCreatedTime;
            if (cost > 10 * 1000) {
                finish();
            }
        }
    }

    @Override
    @CallSuper
    protected void onPause() {
        super.onPause();
        mResumed = false;
    }

    @Override
    @CallSuper
    protected void onStop() {
        mStarted = false;
        super.onStop();
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        mCreated = false;
        super.onDestroy();
    }

    @Override
    @CallSuper
    protected void onRestart() {
        super.onRestart();
        mRestarted++;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void finish() {
        super.finish();
        // 解决某些Android版本(4.4, 7.0, 7.1) 启动Activity 使用 FLAG_ACTIVITY_REORDER_TO_FRONT
        // 导致回退栈 错误回到Launcher界面的问题
        // 参考 https://issuetracker.google.com/issues/36986021
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT
                || Build.VERSION.SDK_INT == Build.VERSION_CODES.N
                || Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1
                        && mIsRestoredToTop && !isTaskRoot()) {
            ActivityManager tasksManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            if (tasksManager != null) {
                try {
                    tasksManager.moveTaskToFront(getTaskId(),
                            ActivityManager.MOVE_TASK_NO_USER_ACTION);
                } catch (Exception e) {
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
    public void setTranslucentStatus() {
        if (isFullScreen()) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
            } else {
                if (isHideNavigation()) {
                    getWindow().getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                    );
                } else {
                    getWindow().getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                    );
                }

            }
            return;
        }
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    protected boolean isFullScreen() {
        return false;
    }

    protected boolean isHideNavigation() {
        return false;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        recheckTranslucent();
    }

    private void recheckTranslucent() {
        if (isFullScreen() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus();
        }
    }

    /**
     * Activity是否已经Create
     */
    public final boolean isActivityCreated() {
        return mCreated;
    }

    /**
     * Activity是否已经Start
     */
    public final boolean isActivityStarted() {
        return mStarted;
    }

    /**
     * Activity是否已经Resumed
     */
    public final boolean isActivityResumed() {
        return mResumed;
    }

    /**
     * Activity是否已经Paused
     */
    public final boolean isActivityPaused() {
        return !mResumed;
    }

    /**
     * Activity是否已经Stopped
     */
    public final boolean isActivityStopped() {
        return !mStarted;
    }

    /**
     * 获取Activity restart的次数
     */
    public final int getRestarted() {
        return mRestarted;
    }
}
