package com.example.pawtnerup.api.request

import com.google.gson.annotations.SerializedName

data class PostQuestionnaireRequest(

	@field:SerializedName("pet_genders")
	val petGenders: List<String>? = null,

	@field:SerializedName("pet_ages")
	val petAges: List<String>? = null,

	@field:SerializedName("breed_ids")
	val breedIds: List<Int>? = null,

	@field:SerializedName("pet_sizes")
	val petSizes: List<String>? = null
)
