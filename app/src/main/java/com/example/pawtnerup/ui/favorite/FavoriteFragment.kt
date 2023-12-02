package com.example.pawtnerup.ui.favorite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pawtnerup.R
import com.example.pawtnerup.api.response.LoginResult
import com.example.pawtnerup.data.paging.LoadingStateAdapter
import com.example.pawtnerup.data.pref.LoginPreferences
import com.example.pawtnerup.databinding.FragmentFavoriteBinding
import com.example.pawtnerup.ui.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var storiesViewModel :  StoriesViewModel
    private lateinit var preference: LoginPreferences
    private lateinit var loginModel: LoginResult
    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater)
        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        val root = inflater.inflate(R.layout.fragment_favorite, container, false)

        storiesViewModel = obtainViewModel(this)
        showLoading(true)
        preference = LoginPreferences(requireContext())
        loginModel = preference.getUser()

        lifecycleScope.launch{
            withContext(Dispatchers.Main){
                if(loginModel.token != null) {
                    binding.rvFavorite.layoutManager = LinearLayoutManager(requireActivity())
                    getData()
                }

            }
        }

        Log.d("FavoriteFragment", "onCreateView: ${getData()}")
        return root
    }
    private fun obtainViewModel(fragment: Fragment): StoriesViewModel {
        val userPreference = LoginPreferences(fragment.requireActivity().application)
        val factory = ViewModelFactory.getInstance(fragment.requireActivity().application, userPreference)
        return ViewModelProvider(this, factory)[StoriesViewModel::class.java]
    }

    private fun getData() {
        showLoading(false)
        val adapter = StoryAdapter()
        binding.rvFavorite.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        storiesViewModel.getListStory.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                adapter.submitData(lifecycle, list)
                Log.d("FavoriteFragment", "getData: $list")
            }
        }

        Log.d("FavoriteFragment", "getData: ${storiesViewModel.getListStory}")
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}