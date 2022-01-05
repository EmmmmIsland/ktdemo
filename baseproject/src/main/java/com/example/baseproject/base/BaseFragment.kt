package com.example.baseproject.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(), ILazyLoad {
    /**
     * 缓存视图，如果视图已经创建，则不再初始化视图
     */
    private var rootView: View? = null

    /**
     * 是否开启懒加载，默认开启
     */
    private var lazyLoadEnable = true

    /**
     * 当前的状态
     */
    private var currentState = ILazyLoad.ANY

    /**
     * 是否已经执行懒加载
     */
    private var hasLazyLoad = false

    /**
     * 当前Fragment是否对用户可见
     */
    private var isVisibleToUser = false

    /**
     * 是否调用了setUserVisibleHint方法。
     * 处理show+add+hide模式下，默认可见Fragment不调用onHiddenChanged方法，进而不执行懒加载方法的问题。
     */
    private var isCallUserVisibleHint = false

    /**
     * 是否开启懒加载，调用此方法建议在getLazyInitState()所返回的状态之前
     */
    protected fun enableLazyLoad(enable: Boolean) {
        this.lazyLoadEnable = enable
    }

    /**
     * 懒加载的调用时机
     */
    protected fun getLazyLoadtState() = ILazyLoad.ON_RESUME

    protected fun setCurrentState(state: Int) {
        this.currentState = state
    }

    /**
     * 是否是在setUserVisibleHint中调用
     */
    protected fun doLazyLoad(callInUserVisibleHint: Boolean) {
        if (!callInUserVisibleHint) {
            if (!isCallUserVisibleHint) isVisibleToUser = !isHidden
        }
        if (lazyLoadEnable && !hasLazyLoad && isVisibleToUser && currentState >= getLazyLoadtState()) {
            hasLazyLoad = true
            lazyLoad()
        }
    }

    protected fun getRootView(): View? {
        return rootView
    }

    protected fun setRootView(view: View) {
        this.rootView = view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setCurrentState(ILazyLoad.ON_ATTACH)
        doLazyLoad(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCurrentState(ILazyLoad.ON_CREATE)
        doLazyLoad(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setCurrentState(ILazyLoad.ON_CREATE_VIEW)
        if (rootView != null) {
            return rootView
        }
        rootView = inflater.inflate(getLayoutId(), container, false)
        initView()
        initData()
        doLazyLoad(false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setCurrentState(ILazyLoad.ON_ACTIVITY_CREATED)
        doLazyLoad(false)
    }

    override fun onStart() {
        super.onStart()
        setCurrentState(ILazyLoad.ON_START)
        doLazyLoad(false)
    }

    override fun onResume() {
        super.onResume()
        setCurrentState(ILazyLoad.ON_RESUME)
        doLazyLoad(false)
    }

    /**
     * 主要针对add+show+hide模式下，Fragment的隐藏与显示调用的方法
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        isVisibleToUser = !hidden
        doLazyLoad(false)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        isCallUserVisibleHint = true
        doLazyLoad(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hasLazyLoad = false
        isVisibleToUser = false
        isCallUserVisibleHint = false
    }

    protected abstract @LayoutRes
    fun getLayoutId(): Int


    override fun lazyLoad() {

    }

    open fun initView(){}
    open fun initData(){}
}