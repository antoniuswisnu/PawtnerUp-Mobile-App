package com.example.pawtnerup.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuestionnaireModel (
    val bio: String?,
    val petPersonality: String?,
) : Parcelable

@Parcelize
data class QuestionnaireModel2 (
    val petSizes: ArrayList<String>?,
    val petAges: ArrayList<String>?,
    val petGenders: ArrayList<String>?,
    val petBreeds: Int?
) : Parcelable