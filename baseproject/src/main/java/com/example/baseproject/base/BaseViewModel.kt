package com.example.baseproject.base

import androidx.lifecycle.*
import com.demo.toolkit.LogUtil
import com.ebook.network.BuildConfig
import com.hellokt.network.RequestCode
import com.hellokt.network.http.base.BaseResponse
import kotlinx.coroutines.launch


open class BaseViewModel : ViewModel() {
    fun <T : BaseResponse> launch(
        baseRepository: BaseRepository<T>,
        complete: (suspend () -> Unit)? = null,
        error: (suspend (errCode: Int, errorMsg: String) -> Unit)? = null,
        success: suspend (rsp: T) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val rsp: T = baseRepository.request()
                success.invoke(rsp)
            } catch (e: NetworkException) {
                error?.invoke(e.code ?: RequestCode.UNKNOW, e.message ?: "")
            } catch (e: Throwable) {
                if (BuildConfig.DEBUG) {
                    LogUtil.e(e.localizedMessage)
                }
//                if(NetworkUtil.isNetworkConected(App.INSTANCE)) {
//                    error?.invoke(RequestCode.UNKNOW, ResourceUtils.getString(R.string.internet_problem))
//                } else {
//                    error?.invoke(RequestCode.UNKNOW, ResourceUtils.getString(R.string.no_network))
//                }
                error?.invoke(RequestCode.UNKNOW, "no_network")
            } finally {
                complete?.invoke()
            }
        }
    }
}