//package com.example.pawtnerup.data.repository
//
//import com.example.pawtnerup.api.response.BreedResponse
//import com.example.pawtnerup.api.response.PetResponse
//import com.example.pawtnerup.api.response.QuestionnaireResponse
//import com.example.pawtnerup.api.retrofit.ApiService
//import retrofit2.Call
//
//class PetRepository(private val apiService: ApiService){
//    fun getPets(): Call<PetResponse> {
//        return apiService.getPets()
//    }
//    fun getBreeds(dogId: String, dogName: String) : Call<BreedResponse> {
//        return apiService.getBreeds()
//    }
//    fun postQuestionnaire(petSizes: ArrayList<String>, petAges: ArrayList<String>, petGenders: ArrayList<String>, petBreeds: Int) : Call<QuestionnaireResponse> {
//        return apiService.postQuestionnaire(
//            petSizes,
//            petAges,
//            petGenders,
//            petBreeds
//        )
//    }
//
////    companion object{
////        @Volatile
////        private var instance: PetRepository? = null
////
////        fun getInstance(context : Context): PetRepository{
////            return instance ?: synchronized(this){
////                if(instance == null){
////                    instance = PetRepository(ApiService.getInstance(context))
////                }
////            }
////        }
////    }
//}