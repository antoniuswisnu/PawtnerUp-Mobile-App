package com.example.pawtnerup.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pawtnerup.R
import com.example.pawtnerup.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater)
        val root = inflater.inflate(R.layout.fragment_favorite, container, false)

        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        favoriteViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.rvFavorite
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        favoriteViewModel.listAdopter.observe(viewLifecycleOwner) {
            val adapter = FavoriteAdapter(ArrayList())
            recyclerView.adapter = adapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


}