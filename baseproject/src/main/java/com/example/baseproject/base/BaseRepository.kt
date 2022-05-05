package com.example.baseproject.base

import com.hellokt.network.NetWorkHelper
import com.hellokt.network.http.RequestMode
import com.hellokt.network.http.RequestUtil
import com.hellokt.network.http.base.BaseErrorBean
import com.hellokt.network.http.base.BaseRequest
import com.hellokt.network.http.base.BaseResponse
import com.hellokt.network.http.base.RequestUrl
import com.demo.toolkit.json.JsonUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import retrofit2.http.*

abstract class BaseRepository<T : BaseResponse> {

    abstract val req: BaseRequest
    abstract val rspClass: Class<T>

    companion object {
        val apiService: ApiService by lazy {
            NetWorkHelper.retrofit.create(ApiService::class.java)
        }
    }

    suspend fun request(): T {
        val jsonResult: String = try {
            execute(req)
        } catch (e: HttpException) {
            var code: Int? = null
            var message: String? = null
            try {
                val errorJson = withContext(Dispatchers.IO) {
                    e.response()?.errorBody()?.string().toString()
                }
                errorJson.let {
                    val errorRsp: BaseErrorBean? = withContext(Dispatchers.Default) {
                        JsonUtil.unpack(it, BaseErrorBean::class.java)
                    }
                    code = errorRsp?.code
                    message = errorRsp?.message
                }
            } catch (ignore: Exception) {

            }

            if (code == null) {
                code = e.code()
            }

            if (message.isNullOrEmpty()) {
                message = e.message()
                if (message.isNullOrBlank()) {
                    message = e.localizedMessage
                }
                if (message.isNullOrBlank()) {
                    message = "network_error"
                }
            }
            throw NetworkException(e.code(), message)
        }
        val rsp: T? = withContext(Dispatchers.Default) {
            JsonUtil.unpack(jsonResult, rspClass)
        }
        return if (rsp != null && rsp.isSuccess()) {
            rsp
        } else {
            throw NetworkException(rsp?.code, rsp?.message)
        }

    }

    private suspend fun execute(req: BaseRequest) = withContext(Dispatchers.IO) {
        val params = if (req !is RequestUrl) {
            req.reflectParameters()
        } else {
            mutableMapOf()
        }
        if (req.useSignatureParams()) {
            req.signatureParams(params)
        }

        when (req.requestMode()) {
            RequestMode.MULTIPART -> {
                apiService.uploadFile(
                    req.api(),
                    MultipartBody.Part.createFormData(
                        "portrait",
                        req.file!!.name,
                        req.file!!.asRequestBody(req.contentType())
                    ),
                    RequestUtil.getHeader().apply {
                        remove("Content-Type")
                    },
                )
            }
            RequestMode.POST -> {
                apiService.post(
                    req.api(),
                    RequestUtil.getHeader(),
                    RequestUtil.getBodyAny(params)
                )
            }
            RequestMode.GET -> {
                apiService.get(
                    req.api(),
                    RequestUtil.getHeader(),
                    RequestUtil.getBodyAny(params)
                )
            }
            RequestMode.PUT -> {
                apiService.put(
                    req.api(),
                    RequestUtil.getHeader(),
                    RequestUtil.getBodyAny(params)
                )
            }
            RequestMode.DELETE -> {
                apiService.delete(
                    req.api(),
                    RequestUtil.getHeader(),
                    RequestUtil.getBodyAny(params)
                )
            }
        }
    }

    interface ApiService {

        @GET
        suspend fun get(
            @Url url: String,
            @HeaderMap headerMap: MutableMap<String, String>,
            @QueryMap body: MutableMap<String, Any>,
        ): String

        @POST
        suspend fun post(
            @Url url: String,
            @HeaderMap headerMap: MutableMap<String, String>,
            @Body body: MutableMap<String, Any>,
        ): String

        @Multipart
        @PUT
        suspend fun uploadFile(
            @Url url: String,
            @Part portrait: MultipartBody.Part,
            @HeaderMap headerMap: MutableMap<String, String>,
        ): String

        @PUT
        suspend fun put(
            @Url url: String,
            @HeaderMap headerMap: MutableMap<String, String>,
            @Body body: MutableMap<String, Any>,
        ): String

        @DELETE
        suspend fun delete(
            @Url url: String,
            @HeaderMap headerMap: MutableMap<String, String>,
            @QueryMap body: MutableMap<String, Any>,
        ): String
    }
}
