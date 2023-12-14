package com.example.pawtnerup.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TokenModel (
    val refreshToken: String? = null
) : Parcelable