package com.example.pawtnerup.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pawtnerup.api.response.ListStoryItem
import com.example.pawtnerup.api.retrofit.ApiService
import com.example.pawtnerup.data.pref.LoginPreferences

class StoryPagingSource(
    private val pref: LoginPreferences,
    private val apiService: ApiService
) : PagingSource<Int, ListStoryItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
//            val token = pref.getUser().token.toString()
            val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLTl2RmZxTmlBbmJZNlNGbmEiLCJpYXQiOjE2OTk5ODk0MTl9.DxF40mLNRl4cacVn9A0B_CYCyvUe9OqbXBE1EOypsFc"
            if (token.isNotEmpty()) {
                val responseData =
                    token.let { apiService.getStories(page, params.loadSize, 0) }
                if (responseData.isSuccessful) {
                    LoadResult.Page(
                        data = responseData.body()?.listStory ?: emptyList(),
                        prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                        nextKey = if (responseData.body()?.listStory.isNullOrEmpty()) null else page + 1
                    )
                } else {
                    LoadResult.Error(Exception("Failed load story"))
                }
            } else {
                LoadResult.Error(Exception("Token empty"))
            }
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}