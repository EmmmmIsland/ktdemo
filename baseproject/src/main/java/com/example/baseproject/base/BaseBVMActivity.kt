package com.example.baseproject.base

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider

abstract class BaseBVMActivity<B : ViewDataBinding, VM : BaseViewModel> : BaseBindingActivity<B>(){

    protected lateinit var viewModel: VM
        private set

    override fun initContentView() {
        super.initContentView()
        injectViewModel()
    }

    protected fun injectViewModel() {
        val vm = createViewModel()
        viewModel = ViewModelProvider(this, BaseViewModel.createViewModelFactory(vm))
            .get(vm::class.java)
        viewModel.application = application
        lifecycle.addObserver(viewModel)
        binding.setVariable(initVariableId(), vm)
    }

    override fun onDestroy() {
        binding.unbind()
        lifecycle.removeObserver(viewModel)
        super.onDestroy()
    }

    protected abstract fun createViewModel(): VM

    protected abstract fun initVariableId() : Int
}