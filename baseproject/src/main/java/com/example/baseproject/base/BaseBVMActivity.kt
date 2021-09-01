package com.example.baseproject.base

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.example.baseproject.extensions.observeNonNull
import com.example.baseproject.extensions.observeNullable
import com.example.hellokt.base.ViewBehavior

abstract class BaseBVMActivity<B : ViewDataBinding, VM : BaseViewModel> : BaseBindingActivity<B>(),
    ViewBehavior {

    protected lateinit var viewModel: VM
        private set

    override fun initContentView() {
        super.initContentView()
        injectViewModel()
        initInternalObserver()
        bindViewModel()
    }

    protected fun injectViewModel() {
        val vm = createViewModel()
        viewModel = ViewModelProvider(this, BaseViewModel.createViewModelFactory(vm))
            .get(vm::class.java)
        viewModel.application = application
        lifecycle.addObserver(viewModel)
    }

    override fun onDestroy() {
        binding.unbind()
        lifecycle.removeObserver(viewModel)
        super.onDestroy()
    }

    protected fun initInternalObserver() {
        viewModel._loadingEvent.observeNonNull(this) {
            showLoadingView(it)
        }
        viewModel._emptyPageEvent.observeNonNull(this) {
            showEmptyView(it)
        }

        viewModel._pageNavigationEvent.observeNonNull(this) {
            navigate(it)
        }
        viewModel._backPressEvent.observeNullable(this) {
            backPress(it)
        }
        viewModel._finishPageEvent.observeNullable(this) {
            finishPage(it)
        }
    }

    protected abstract fun createViewModel(): VM

    protected abstract fun bindViewModel()
}