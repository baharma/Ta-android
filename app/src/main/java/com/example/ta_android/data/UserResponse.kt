package com.example.ta_android.data

import com.google.gson.annotations.SerializedName

data class UserResponse (
    @field:SerializedName("message")
    val message : String?=null,
    @field:SerializedName("access_token")
    val access_token : String?=null,
    @field:SerializedName("token_type")
    val token_type : String?=null
)