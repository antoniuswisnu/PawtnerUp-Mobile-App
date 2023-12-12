package com.example.pawtnerup.api.request

import com.google.gson.annotations.SerializedName

data class PostPreferencesRequest(

	@field:SerializedName("preference")
	val preference: String? = null,

	@field:SerializedName("pet_id")
	val petId: Int? = null
)
