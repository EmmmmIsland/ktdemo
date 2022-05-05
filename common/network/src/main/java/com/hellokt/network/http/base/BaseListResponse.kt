package com.hellokt.network.http.base

import com.google.gson.annotations.SerializedName

open class BaseListResponse<T> : BaseResponse() {
    override val data: CommonListResponseModel<T>? = null
}

class CommonListResponseModel<T>(
    @SerializedName("items") @JvmField var items: List<T>?,
    @SerializedName("callback") @JvmField var callback: String?
)