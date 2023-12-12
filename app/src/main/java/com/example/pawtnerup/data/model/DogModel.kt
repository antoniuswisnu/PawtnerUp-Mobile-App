package com.example.pawtnerup.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DogModel (
    val id: Int? = null,
    val name: String? = null,
    val gender: String? = null,
    val breed: String? = null,
    val bornDate: String? = null,
    val shelterId: Int? = null,
    val sterilizationStatus: String? = null,
    val rescueStory: String? = null,
    val media: List<String?>? = null
): Parcelable