package com.example.pawtnerup.api.response

import com.google.gson.annotations.SerializedName

data class AdopterResponse(

	@field:SerializedName("data")
	val data: DataAdopter? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class PetAdopter(

	@field:SerializedName("shelter")
	val shelter: ShelterAdopter? = null,

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
	val bornDate: String? = null,

	@field:SerializedName("labels")
	val labels: List<String?>? = null
)

data class PreferencesItem(

	@field:SerializedName("preference")
	val preference: String? = null,

	@field:SerializedName("pet")
	val pet: PetAdopter? = null,

	@field:SerializedName("pet_id")
	val petId: Int? = null
)

data class DataAdopter(

	@field:SerializedName("preferences")
	val preferences: List<PreferencesItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("profile_picture")
	val profilePicture: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class ShelterAdopter(

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
