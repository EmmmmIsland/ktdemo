package com.example.hellokt.ui.rx

import android.os.Bundle
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.demo.toolkit.ext.onClickSafe
import com.example.baseproject.base.BaseVmActivity
import com.example.baseproject.router.ARouterConfig
import com.example.hellokt.databinding.ActivityRxBinding
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.schedulers.Schedulers

import io.reactivex.ObservableOnSubscribe




@Route(path = ARouterConfig.AROUTER_PATH_RX_ACTIVITY)
class RxActivity : BaseVmActivity<ActivityRxBinding>() {

    companion object{
        val ATAG = "RXRXRX"
    }

    override fun getLayout(): ActivityRxBinding =
        ActivityRxBinding.inflate(layoutInflater)

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        binding.toolBar.setNavigationOnClickListener { finish() }

        binding.tvAction.onClickSafe {
            Observable.just("aa", "bb")
                .subscribe(object : Consumer<String> {
                    override fun accept(t: String) {
                        Log.d(ATAG, t)
                    }
                },object : Consumer<Throwable> {
                    override fun accept(t: Throwable?) {
                        Log.d(ATAG,"error" )
                    }
                },object : Action{
                    override fun run() {
                        Log.d(ATAG,"complete" )
                    }

                })


            Observable.just("cc", "dd")
                .subscribe(object:Observer<String>{
                    override fun onSubscribe(d: Disposable) {
                        Log.d(ATAG,"onSubscribe" )
                    }

                    override fun onNext(t: String) {
                        Log.d(ATAG,t )
                    }

                    override fun onError(e: Throwable) {
                        Log.d(ATAG,"onError" )
                    }

                    override fun onComplete() {
                        Log.d(ATAG,"onComplete" )
                    }

                })


        }

        binding.tvMap.onClickSafe {
            Observable.just(12,22)
                .map { integer -> integer.toString() + "AASS" }
                .subscribe(object : Observer<String> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(ATAG,"onSubscribe" )
                    }

                    override fun onNext(t: String) {
                        Log.d(ATAG,t )
                    }

                    override fun onError(e: Throwable) {
                        Log.d(ATAG,"onError" )
                    }

                    override fun onComplete() {
                        Log.d(ATAG,"onComplete" )
                    }

                })
        }
    }

    override fun initData() {

    }

}