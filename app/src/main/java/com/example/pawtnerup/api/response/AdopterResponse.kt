package com.example.pawtnerup.api.response

import com.google.gson.annotations.SerializedName

data class AdopterResponse(

	@field:SerializedName("data")
	val dataAdopter: DataAdopter? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataAdopter(

	@field:SerializedName("preferences")
	val preferences: List<PreferencesItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("bio")
	val bio: String? = null,

	@field:SerializedName("profile_picture")
	val profilePicture: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class PreferencesItem(

	@field:SerializedName("pet_id")
	val petId: Int? = null,

	@field:SerializedName("pet")
	val pet: PetAdopter? = null,

	@field:SerializedName("preference")
	val preference: String? = null

)

data class PetAdopter(

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

	@field:SerializedName("labels")
	val labels: List<String?>? = null,

	@field:SerializedName("shelter_id")
	val shelterId: Int? = null,

	@field:SerializedName("sterilization_status")
	val sterilizationStatus: String? = null,

	@field:SerializedName("breed")
	val breed: String? = null,

	@field:SerializedName("born_date")
	val bornDate: String? = null
)
