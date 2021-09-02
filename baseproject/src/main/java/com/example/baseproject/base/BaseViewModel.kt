package com.example.baseproject.base

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*


abstract class BaseViewModel : ViewModel(), ViewModelLifecycle {
    @SuppressLint("StaticFieldLeak")
    lateinit var application: Application

    private lateinit var lifcycleOwner: LifecycleOwner

    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {
        this.lifcycleOwner = owner
    }

    override fun onCreate() {

    }

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {

    }

    companion object {
        @JvmStatic
        fun <T : BaseViewModel> createViewModelFactory(viewModel: T): ViewModelProvider.Factory {
            return ViewModelFactory(viewModel)
        }
    }
}

/**
 * 创建ViewModel的工厂，以此方法创建的ViewModel，可在构造函数中传参
 */
class ViewModelFactory(val viewModel: BaseViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModel as T
    }
}