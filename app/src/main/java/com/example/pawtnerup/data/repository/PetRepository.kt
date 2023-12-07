package com.example.pawtnerup.data.repository

import android.content.Context
import com.example.pawtnerup.api.response.BreedResponse
import com.example.pawtnerup.api.response.PetResponse
import com.example.pawtnerup.api.response.QuestionnaireResponse
import com.example.pawtnerup.api.retrofit.ApiService

class PetRepository(private val apiService: ApiService){
    suspend fun getPets(): PetResponse {
        return apiService.getPets()
    }
    suspend fun getBreeds(dogId: String, dogName: String) : BreedResponse {
        return apiService.getBreeds(dogId, dogName)
    }
    suspend fun postQuestionnaire(bio: String, petPersonality: String, petSizes: ArrayList<String>, petAges: ArrayList<String>, petGenders: ArrayList<String>, petBreeds: Int) :QuestionnaireResponse {
        return apiService.postQuestionnaire(
            bio,
            petPersonality,
            petSizes,
            petAges,
            petGenders,
            petBreeds
        )
    }

//    companion object{
//        @Volatile
//        private var instance: PetRepository? = null
//
//        fun getInstance(context : Context): PetRepository{
//            return instance ?: synchronized(this){
//                if(instance == null){
//                    instance = PetRepository(ApiService.getInstance(context))
//                }
//            }
//        }
//    }
}