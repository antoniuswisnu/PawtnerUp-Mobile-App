package com.example.pawtnerup.api.retrofit

import com.example.pawtnerup.api.request.PostPreferencesRequest
import com.example.pawtnerup.api.request.PostQuestionnaireRequest
import com.example.pawtnerup.api.request.PostRefreshTokenRequest
import com.example.pawtnerup.api.response.AdopterResponse
import com.example.pawtnerup.api.response.BreedResponse
import com.example.pawtnerup.api.response.PetResponse
import com.example.pawtnerup.api.response.PreferencesResponse
import com.example.pawtnerup.api.response.QuestionnaireResponse
import com.example.pawtnerup.api.response.RecommendationResponse
import com.example.pawtnerup.api.response.RefreshResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("pets/breeds")
    fun getBreeds(
        @Query("size") size: String,
    ): Call<BreedResponse>

    @POST("adopters/questionnaire")
    fun postQuestionnaire(
        @Body request: PostQuestionnaireRequest
    ): Call<QuestionnaireResponse>

    @GET("adopters/me/preferences")
    fun getAdopter(
        ): Call<AdopterResponse>

    @POST("adopters/me/preferences")
    fun postPreference(
        @Body request: PostPreferencesRequest
    ): Call<PreferencesResponse>

    @GET("adopters/me/recommendations")
    fun getRecommendations(
    ): Call<RecommendationResponse>

    @POST("auth/adopter/generate-refresh-token")
    fun postRefreshToken(
        @Body code: PostRefreshTokenRequest
    ): Call<RefreshResponse>

    @GET("shelters/pets/{pet_id}")
    fun getPet(
        @Path("pet_id") petId: String
    ): Call<PetResponse>

    @DELETE("adopters/me/preferences/{pet_id}")
    fun deletePet(
        @Path("pet_id") petId: String
    ): Call<PetResponse>
}