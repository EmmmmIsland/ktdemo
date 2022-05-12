package com.example.hellokt.ui.login

import com.hellokt.network.http.RequestMode
import com.hellokt.network.http.base.BaseRequest
import com.hellokt.network.http.base.BaseResponse
import com.example.baseproject.base.BaseRepository
import com.example.hellokt.model.User
import com.google.gson.annotations.SerializedName

class LoginRepository : BaseRepository<LoginRepository.Rsp>() {
    override val req = Req()
    override val rspClass: Class<Rsp> = Rsp::class.java

    class Req() : BaseRequest() {

        override fun api(): String {
            return "demo_war/HelloServlet"
        }

        override fun requestMode(): RequestMode {
            return RequestMode.GET
        }
    }

    class Rsp : BaseResponse() {
        override val data: User? = null
    }
}