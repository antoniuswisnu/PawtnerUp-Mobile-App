package com.example.pawtnerup.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pawtnerup.api.response.RecommendationResponse
import com.example.pawtnerup.databinding.ItemLabelBinding

class LabelAdapter(val context: Context, private val list : ArrayList<RecommendationResponse>) : RecyclerView.Adapter<LabelAdapter.HomeViewHolder>() {
    inner class HomeViewHolder(val binding : ItemLabelBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(ItemLabelBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val dog = list[position].data?.get(position)
        for (i in 0 until dog?.labels?.size!! - 1) {
            holder.binding.tvLabel.text = dog.labels[i]
            dog.labels[i]?.let { Log.d("LabelAdapter", it) }
        }
//        holder.binding.tvLabel.text = dog?.labels?.get(0)


    }
}