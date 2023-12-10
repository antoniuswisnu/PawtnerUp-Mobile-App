package com.example.pawtnerup.api.retrofit

import com.example.pawtnerup.api.response.AdopterResponse
import com.example.pawtnerup.api.response.BreedResponse
import com.example.pawtnerup.api.response.PetResponse
import com.example.pawtnerup.api.response.PreferencesResponse
import com.example.pawtnerup.api.response.QuestionnaireResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("shelters/pets/me")
    fun getPets(): Call<PetResponse>

    @GET("pets/breeds")
    fun getBreeds(): Call<BreedResponse>

    @FormUrlEncoded
    @POST("adopters/questionnaire")
    fun postQuestionnaire(
        @Field("pet_sizes") petSizes: ArrayList<String>,
        @Field("pet_ages") petAges: ArrayList<String>,
        @Field("pet_genders") petGenders: ArrayList<String>,
        @Field("pet_breeds") petBreeds: ArrayList<Int>,
    ): Call<QuestionnaireResponse>

    @GET("adopters/me/preferences")
    fun getAdopter(): Call<AdopterResponse>

    @GET("adopters/me/preferences")
    fun postPreference(
        @Field("pet_id") petId: Int,
        @Field("preference") preference: String
    ): PreferencesResponse
}