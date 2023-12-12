package com.example.pawtnerup.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pawtnerup.api.response.RecommendationResponse
import com.example.pawtnerup.databinding.ItemDogLayoutBinding

class HomeAdapter(val context: Context, private val list : ArrayList<RecommendationResponse>) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    inner class HomeViewHolder(val binding : ItemDogLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(ItemDogLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.binding.tvDogName.text = list[position].data?.get(position)?.name
        holder.binding.tvDogAge.text = list[position].data?.get(position)?.estimateAge.toString()
        holder.binding.tvDogBio.text = list[position].data?.get(position)?.rescueStory

        Glide.with(context)
            .load(list[position].data?.get(position)?.media?.get(0))
            .into(holder.binding.ivDog)

    }
}