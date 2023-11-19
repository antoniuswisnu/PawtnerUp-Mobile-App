package com.example.pawtnerup.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.pawtnerup.api.response.ListStoryItem
import com.example.pawtnerup.api.retrofit.ApiConfig
import com.example.pawtnerup.api.retrofit.ApiService
import com.example.pawtnerup.data.paging.StoryPagingSource
import com.example.pawtnerup.data.pref.LoginPreferences

class StoryPagingRepository constructor(application: Application, private val userPreferences: LoginPreferences) {

//    private val token = userPreferences.getUser().token.toString()
    private val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLTl2RmZxTmlBbmJZNlNGbmEiLCJpYXQiOjE2OTk5ODk0MTl9.DxF40mLNRl4cacVn9A0B_CYCyvUe9OqbXBE1EOypsFc"
    private val apiService: ApiService = ApiConfig.getApiService(token)

    fun getStories(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(userPreferences, apiService)
            }
        ).liveData
    }
}