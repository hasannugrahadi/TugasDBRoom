package com.app.tugaskelompok.ui.home.network

import com.app.tugaskelompok.ui.home.model.ResponseDetailUser
import com.app.tugaskelompok.ui.home.model.ResponseUserGithub
import com.google.firebase.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GithubService {
    @JvmSuppressWildcards
    @GET("users")
    suspend fun getUserGithub(): MutableList<ResponseUserGithub.Item>
    @JvmSuppressWildcards
    @GET("users/{username}")
    suspend fun getDetailUserGithub(@Path("username") username: String): ResponseDetailUser
}