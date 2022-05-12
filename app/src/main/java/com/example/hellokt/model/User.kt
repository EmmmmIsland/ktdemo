package com.example.hellokt.model

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("id") @JvmField var id : Int?,
    @SerializedName("name") @JvmField var name : String?,
                 @SerializedName("age") @JvmField var age : String?)


