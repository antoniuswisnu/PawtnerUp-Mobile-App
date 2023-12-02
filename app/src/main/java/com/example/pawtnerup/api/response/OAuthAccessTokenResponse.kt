package com.example.pawtnerup.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class OAuthAccessTokenResponse(

    @field:SerializedName("access_token")
    val accessToken: String,

    @field:SerializedName("refresh_token")
    val refreshToken: String? = null,

    @field:SerializedName("expires_in") val
    expiresInSeconds: Int? = null,

    @field:SerializedName("token_type")
    val tokenType: String? = null,

    @field:SerializedName("scope")
    val scopes: List<String>? = null,
) : Parcelable