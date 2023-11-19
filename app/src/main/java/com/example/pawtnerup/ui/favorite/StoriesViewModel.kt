package com.example.pawtnerup.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pawtnerup.api.response.ListStoryItem
import com.example.pawtnerup.data.pref.LoginPreferences
import com.example.pawtnerup.data.repository.StoryPagingRepository

class StoriesViewModel (application: Application,
                        userPreference: LoginPreferences
) : ViewModel() {
    private val repository = StoryPagingRepository(application, userPreference)

    val getListStory: LiveData<PagingData<ListStoryItem>> =
        repository.getStories().cachedIn(viewModelScope)
}