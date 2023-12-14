package com.example.pawtnerup.api.request

import com.google.gson.annotations.SerializedName

data class PostRefreshTokenRequest (
    @field:SerializedName("code")
    val code: String? = null
)