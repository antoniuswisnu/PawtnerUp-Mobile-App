package com.example.pawtnerup.api.response

import com.google.gson.annotations.SerializedName

data class PetResponse(

	@field:SerializedName("data")
	val data: List<PetItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class PetItem(

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("rescue_story")
	val rescueStory: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("media")
	val media: List<String?>? = null,

	@field:SerializedName("estimate_age")
	val estimateAge: Int? = null,

	@field:SerializedName("sterilization_status")
	val sterilizationStatus: String? = null,

	@field:SerializedName("breed")
	val breed: String? = null
)
