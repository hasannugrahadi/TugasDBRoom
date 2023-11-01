package com.app.tugaskelompok.ui.home

import retrofit2.http.GET

interface GitHubService {
    @GET("users")
    suspend fun getUsers(): List<GithubUser>
}