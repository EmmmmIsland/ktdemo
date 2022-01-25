package com.example.baseproject.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding


abstract class BaseFragment<T : ViewBinding> : NestedLifecycleFragment() {

    private var _binding: T? = null

    val binding get() = _binding!!

    /**
     * 来源
     */
    var sourceName = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = getLayout(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
        initViewModel()
        initData()
    }

    protected open fun pageOpenRecordParams(): Map<String?, Any?>? {
        return null
    }

    protected abstract fun getLayout(inflater: LayoutInflater, container: ViewGroup?): T

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @CallSuper
    open fun initView(savedInstanceState: Bundle?) {

    }

    /**
     * fragment里使用LifecycleOwner时使用 #viewLifecycleOwner 不要使用 this
     */
    open fun initViewModel() {

    }

    open fun initData() {}

    open fun pageId(): String {
        return ""
    }
}