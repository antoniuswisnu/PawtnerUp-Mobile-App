package com.example.pawtnerup.ui.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pawtnerup.api.request.PostPreferencesRequest
import com.example.pawtnerup.api.response.PreferencesResponse
import com.example.pawtnerup.api.response.RecommendationResponse
import com.example.pawtnerup.data.repository.PetRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val repository: PetRepository) : ViewModel() {

    private val _recommendations = MutableLiveData<Collection<RecommendationResponse>>()
    val recommendations: LiveData<Collection<RecommendationResponse>> get() = _recommendations

    private val _likeResponse = MutableLiveData<PreferencesResponse?>()
    val likeResponse: LiveData<PreferencesResponse?> get() = _likeResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDog() {
        repository.getRecommendations(object : Callback<RecommendationResponse> {
            override fun onResponse(
                call: Call<RecommendationResponse>,
                response: Response<RecommendationResponse>
            ) {
                _isLoading.value = true
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val body = response.body()
                    if (body != null) {
                        _recommendations.value = listOf(body)
                    }
                } else {
                    _isLoading.value = false
                    _error.value = "No data found"
                    Log.e(TAG, "onResponse: ${response.message()} ${response.code()} ${response.errorBody()} ${response.raw()}")
                }
            }

            override fun onFailure(call: Call<RecommendationResponse>, t: Throwable) {
                _error.value = t.message
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }


    fun postLikeDog(context: Context, petId: Int?) {
        repository.postPreference(PostPreferencesRequest("LIKE", petId),
            object : Callback<PreferencesResponse> {
                override fun onResponse(
                    call: Call<PreferencesResponse>,
                    response: Response<PreferencesResponse>
                ) {
                    _isLoading.value = true
                    if (response.isSuccessful) {
                        _isLoading.value = false
                        val body = response.body()
                        _likeResponse.value = body
                        Toast.makeText(context, "You have liked this dog", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<PreferencesResponse>, t: Throwable) {
                    _error.value = t.message
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
    }

    fun postDislikeDog(context: Context, petId: Int?) {
        repository.postPreference(PostPreferencesRequest("DISLIKE", petId),
            object : Callback<PreferencesResponse> {
                override fun onResponse(
                    call: Call<PreferencesResponse>,
                    response: Response<PreferencesResponse>
                ) {
                    _isLoading.value = true
                    if (response.isSuccessful) {
                        _isLoading.value = false
                        val body = response.body()
                        _likeResponse.value = body
                        Toast.makeText(context, "You have Dislike this dog", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<PreferencesResponse>, t: Throwable) {
                    _error.value = t.message
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }

}