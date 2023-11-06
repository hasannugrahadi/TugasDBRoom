package com.app.tugaskelompok.model

import com.google.gson.annotations.SerializedName

data class ResponseUserItem(

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

)
