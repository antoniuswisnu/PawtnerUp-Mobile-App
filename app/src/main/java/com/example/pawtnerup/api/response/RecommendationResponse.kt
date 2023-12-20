package com.example.pawtnerup.api.response

import com.google.gson.annotations.SerializedName

data class RecommendationResponse(

	@field:SerializedName("data")
	val data: List<RecommendationItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ShelterItemRecommendation(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("contact_email")
	val contactEmail: String? = null
)

data class RecommendationItem(

	@field:SerializedName("shelter")
	val shelter: ShelterItemRecommendation? = null,

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
	val estimateAge: Float? = null,

	@field:SerializedName("sterilization_status")
	val sterilizationStatus: String? = null,

	@field:SerializedName("breed")
	val breed: String? = null,

	@field:SerializedName("labels")
	val labels: List<String?>? = null
)
