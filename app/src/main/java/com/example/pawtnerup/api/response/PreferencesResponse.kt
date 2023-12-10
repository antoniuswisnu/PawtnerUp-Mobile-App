package com.example.pawtnerup.api.response

import com.google.gson.annotations.SerializedName

data class PreferencesResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Pet(

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

	@field:SerializedName("shelter_id")
	val shelterId: Int? = null,

	@field:SerializedName("sterilization_status")
	val sterilizationStatus: String? = null,

	@field:SerializedName("breed")
	val breed: String? = null,

	@field:SerializedName("born_date")
	val bornDate: String? = null
)

data class Data(

	@field:SerializedName("preference")
	val preference: String? = null,

	@field:SerializedName("pet")
	val pet: Pet? = null,

	@field:SerializedName("pet_id")
	val petId: Int? = null
)
