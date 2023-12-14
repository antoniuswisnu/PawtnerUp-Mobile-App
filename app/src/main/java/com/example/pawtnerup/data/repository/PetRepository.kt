package com.example.pawtnerup.data.repository

import com.example.pawtnerup.api.request.PostPreferencesRequest
import com.example.pawtnerup.api.response.AdopterResponse
import com.example.pawtnerup.api.response.PreferencesResponse
import com.example.pawtnerup.api.response.RecommendationResponse
import com.example.pawtnerup.api.retrofit.ApiService
import com.example.pawtnerup.data.model.TokenModel
import retrofit2.Callback

class PetRepository(private val apiService: ApiService){
    fun getAdopter(callback: Callback<AdopterResponse>) {
        val client = apiService.getAdopter()
        client.enqueue(callback)
    }

    fun getRecommendations(callback: Callback<RecommendationResponse>) {
        val client = apiService.getRecommendations()
        client.enqueue(callback)
    }

    fun postPreference(request: PostPreferencesRequest, callback: Callback<PreferencesResponse>) {
        val client = apiService.postPreference(request)
        client.enqueue(callback)
    }



}