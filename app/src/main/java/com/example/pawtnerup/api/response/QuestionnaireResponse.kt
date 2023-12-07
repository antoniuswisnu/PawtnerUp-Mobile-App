package com.example.pawtnerup.api.response

import com.google.gson.annotations.SerializedName

data class QuestionnaireResponse(

	@field:SerializedName("bio")
	val bio: String? = null,

	@field:SerializedName("pet_genders")
	val petGenders: ArrayList<String>? = null,

	@field:SerializedName("pet_ages")
	val petAges: ArrayList<String>? = null,

	@field:SerializedName("breed_ids")
	val breedIds: List<Int>? = null,

	@field:SerializedName("pet_personality")
	val petPersonality: String? = null,

	@field:SerializedName("pet_sizes")
	val petSizes: ArrayList<String>? = null
)
