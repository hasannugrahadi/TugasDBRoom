package com.app.tugaskelompok.network

import com.app.tugaskelompok.model.ResponseUser
import com.app.tugaskelompok.model.ResponseUserDetail
import com.app.tugaskelompok.model.ResponseUserItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users")
    fun getUserGithub(): Call<List<ResponseUserItem>>

    @GET("users/{username}")
    fun getUserDetails(@Path("username") username: String): Call<ResponseUserDetail>
}