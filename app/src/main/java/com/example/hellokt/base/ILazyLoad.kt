package com.example.hellokt.base

interface ILazyLoad {

    companion object {
        const val ON_ATTACH = 1
        const val ON_CREATE = 2
        const val ON_CREATE_VIEW = 3
        const val ON_ACTIVITY_CREATED = 4
        const val ON_START = 5
        const val ON_RESUME = 6
        const val ANY = 7
    }

    /**
     * 延迟加载
     */
    fun lazyLoad()
}