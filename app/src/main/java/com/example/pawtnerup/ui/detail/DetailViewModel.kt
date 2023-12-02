package com.example.pawtnerup.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pawtnerup.api.response.Story
import com.example.pawtnerup.di.Injection
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {

//    private val storyRepository = Injection.provideStoryRepository(application)

    private val _detailStory = MutableLiveData<Story?>()
    val detailStory: MutableLiveData<Story?> = _detailStory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailStories(id: String){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                _isLoading.value = false
//                val response = storyRepository.getDetailStory(id)
//                _detailStory.postValue(response)
            } catch (e: Exception) {
                // Handle exception and show error message

            }
        }
    }
}