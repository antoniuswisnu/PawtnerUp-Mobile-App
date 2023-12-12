package com.example.pawtnerup.data.repository

import com.example.pawtnerup.api.response.AdopterResponse
import com.example.pawtnerup.api.response.BreedResponse
import com.example.pawtnerup.api.response.PetResponse
import com.example.pawtnerup.api.response.PreferencesResponse
import com.example.pawtnerup.api.response.QuestionnaireResponse
import com.example.pawtnerup.api.retrofit.ApiService
import retrofit2.Call

class PetRepository(private val apiService: ApiService){
    fun getPets(): Call<PetResponse> {
        return apiService.getPets()
    }
//    fun getBreeds(size: String) : Call<BreedResponse> {
//        return apiService.getBreeds(
//            size
//        )
//    }
//    fun postQuestionnaire(petSizes: ArrayList<String>, petAges: ArrayList<String>, petGenders: ArrayList<String>, petBreeds: ArrayList<Int>) : Call<QuestionnaireResponse> {
//        return apiService.postQuestionnaire(
//            petSizes,
//            petAges,
//            petGenders,
//            petBreeds
//        )
//    }

    fun getAdopter() : Call<AdopterResponse> {
        return apiService.getAdopter()
    }

    fun postPreference(petId: Int, preference: String) : PreferencesResponse {
        return apiService.postPreference(
            petId,
            preference
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