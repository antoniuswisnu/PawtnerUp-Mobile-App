package com.example.pawtnerup.api.retrofit

import com.example.pawtnerup.api.response.BreedResponse
import com.example.pawtnerup.api.response.PetResponse
import com.example.pawtnerup.api.response.QuestionnaireResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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



}