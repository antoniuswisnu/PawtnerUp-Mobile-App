package com.example.pawtnerup.data.repository

import com.example.pawtnerup.api.response.Story
import com.example.pawtnerup.api.retrofit.ApiService

class StoryRepository(private val apiService: ApiService) {
    suspend fun getDetailStory(id: String) : Story? {
        return apiService.getDetailStories(id).story
    }
    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(apiService: ApiService): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService).also { instance = it }
            }
    }
}