package com.example.pawtnerup.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DogModel (
    val id: Int?,
    val name: String?,
    val age: Int?,

): Parcelable