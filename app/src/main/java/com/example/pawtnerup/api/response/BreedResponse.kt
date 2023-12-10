package com.example.pawtnerup.api.response

import com.google.gson.annotations.SerializedName

data class BreedResponse(

	@field:SerializedName("data")
	val data: List<BreedItem?>,

	@field:SerializedName("message")
	val message: String? = null
)

data class BreedItem(

	@field:SerializedName("size")
	val size: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
