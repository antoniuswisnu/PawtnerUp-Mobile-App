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
    val media: List<String?>? = null,
    val phoneNumber: String? = null,
    val shelterName: String? = null,
    val shelterAddress: String? = null,
    val labels: List<String?>? = null
): Parcelable

@Parcelize
data class DogRecommendationModel (
    val id: Int? = null,
    val name: String? = null,
    val gender: String? = null,
    val breed: String? = null,
    val estimateAge: String? = null,
    val sterilizationStatus: String? = null,
    val rescueStory: String? = null,
    val media: List<String?>? = null,
    val shelterName: String? = null,
    val shelterAddress: String? = null,
    val labels: List<String?>? = null
): Parcelable