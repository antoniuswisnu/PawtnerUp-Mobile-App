package com.example.pawtnerup.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val email: String?,
    val token: String?,
    val displayName: String?,
    val photoUrl: String?
) : Parcelable