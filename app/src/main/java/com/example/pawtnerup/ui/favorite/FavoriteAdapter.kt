package com.example.pawtnerup.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pawtnerup.api.response.PreferencesItem
import com.example.pawtnerup.databinding.ItemDogBinding

class FavoriteAdapter(private val listDog: List<PreferencesItem>)
    : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: PreferencesItem)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listDog.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dog = listDog[position]
        val nameDog = dog.pet?.name
        val breedDog = dog.pet?.breed
        val photoDog = dog.pet?.media

        holder.binding.tvDogName.text = nameDog
        holder.binding.tvDogType.text = breedDog
        Glide.with(holder.itemView.context)
            .load(photoDog)
            .into(holder.binding.ivDog)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listDog[holder.adapterPosition])
        }
    }

    class ViewHolder (val binding: ItemDogBinding): RecyclerView.ViewHolder(binding.root)

}
