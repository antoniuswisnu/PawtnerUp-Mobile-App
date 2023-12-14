package com.example.pawtnerup.api.response

import com.google.gson.annotations.SerializedName

data class DeletePetResponse(

	@field:SerializedName("message")
	val message: String? = null
)
