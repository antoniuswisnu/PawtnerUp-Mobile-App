package com.example.pawtnerup.api.response

import com.google.gson.annotations.SerializedName

data class RefreshResponse(

	@field:SerializedName("data")
	val data: DataRefresh? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataRefresh(

	@field:SerializedName("access_token")
	val accessToken: String? = null,

	@field:SerializedName("refresh_token")
	val refreshToken: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("given_name")
	val givenName: String? = null,

	@field:SerializedName("family_name")
	val familyName: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("picture")
	val picture: String? = null
)
