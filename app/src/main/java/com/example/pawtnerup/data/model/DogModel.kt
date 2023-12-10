package com.example.pawtnerup.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DogModel (
    val gender: String? = null,
    val name: String? = null,
    val rescueStory: String? = null,
    val id: Int? = null,
    val media: List<String?>? = null,
    val shelterId: Int? = null,
    val sterilizationStatus: String? = null,
    val breed: String? = null,
    val bornDate: String? = null
): Parcelable