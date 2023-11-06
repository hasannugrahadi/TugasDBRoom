package com.app.tugaskelompok.model

import com.google.gson.annotations.SerializedName

data class ResponseUser(

    @field:SerializedName("response")
    val responseUser: List<ResponseUserItem>? = null

)