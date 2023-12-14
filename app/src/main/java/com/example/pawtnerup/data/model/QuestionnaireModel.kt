package com.example.pawtnerup.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuestionnaireModel (
    val petSizes: ArrayList<String>?,
    val petAges: ArrayList<String>?,
    val petGenders: ArrayList<String>?,
    val petBreeds: ArrayList<Int>?
) : Parcelable