package com.app.tugaskelompok.model

import com.google.gson.annotations.SerializedName

data class ResponseUserDetail(

	@field:SerializedName("bio")
	val bio: String? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("company")
	val company: String? = null,

	@field:SerializedName("public_repos")
	val publicRepos: Int? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("following")
	val following: Int? = null,

	@field:SerializedName("followers")
	val followers: Int? = null,

	@field:SerializedName("blog")
	val blog: String? = null,

	@field:SerializedName("twitter_username")
	val twitterUsername: Any? = null,


	)
