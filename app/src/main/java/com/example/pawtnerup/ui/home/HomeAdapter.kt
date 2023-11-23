package com.example.pawtnerup.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pawtnerup.data.pref.DogModel
import com.example.pawtnerup.databinding.ItemDogLayoutBinding

class HomeAdapter(val context: Context, private val list : ArrayList<DogModel>) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    inner class HomeViewHolder(val binding : ItemDogLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(ItemDogLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.binding.tvDogName.text = list[position].name
        holder.binding.tvDogAge.text = list[position].age
        holder.binding.tvDogBio.text = list[position].bio
        holder.binding.tvLocation.text = list[position].location
        Glide.with(context).load(list[position].image).into(holder.binding.ivDog)

        Log.d("HomeFragment", list[position].name)
    }
}