package com.example.pawtnerup.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pawtnerup.api.response.AdopterResponse
import com.example.pawtnerup.api.response.PreferencesItem
import com.example.pawtnerup.data.repository.PetRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteViewModel(private val repository: PetRepository) : ViewModel() {

    private val _adopterData = MutableLiveData<List<PreferencesItem?>?>()
    val adopterData: LiveData<List<PreferencesItem?>?> get() = _adopterData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchData() {
        repository.getAdopter(object : Callback<AdopterResponse> {
            override fun onResponse(call: Call<AdopterResponse>, response: Response<AdopterResponse>) {
                _isLoading.value = true
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val body = response.body()?.data
                    val hasLikePreference = body?.preferences?.filter { it?.preference == "LIKE" }

                    if (!hasLikePreference.isNullOrEmpty()) {
                        _adopterData.value = hasLikePreference
                    } else {
                        _error.value = "No data likes"
                    }
                } else {
                    _isLoading.value = false
                    _error.value = "Request failed with code: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<AdopterResponse>, t: Throwable) {
                _error.value = "Request failed: ${t.message}"
            }
        })
    }
}