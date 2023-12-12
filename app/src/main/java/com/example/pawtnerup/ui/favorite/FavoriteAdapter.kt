package com.example.pawtnerup.ui.favorite

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pawtnerup.api.response.DataAdopter
import com.example.pawtnerup.data.model.DogModel
import com.example.pawtnerup.databinding.ItemDogBinding
import com.example.pawtnerup.ui.detail.DetailActivity

class FavoriteAdapter(val context: Context, private val listDog: DataAdopter)
    : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

//    private lateinit var onItemClickCallback: OnItemClickCallback
//
//    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback
//    }
//
//    interface OnItemClickCallback {
//        fun onItemClicked(data: DataAdopter)
//    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ${listDog.preferences?.size}")
        return listDog.preferences?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dog = listDog.preferences?.get(position)?.pet!!
        val nameDog = dog.name
        val breedDog = dog.breed
        val photoDog = "https://igneous-walker-404307.et.r.appspot.com/${dog.media?.get(0).toString()}"
//        val photoDog = dog.media?.get(0).toString()

        holder.binding.tvDogName.text = nameDog
        holder.binding.tvDogType.text = breedDog
        Glide.with(context)
            .load(photoDog)
            .override(100, 100)
            .into(holder.binding.ivDog)

//        Picasso.get()
//            .load(photoDog)
//            .resize(100, 100)
//            .into(holder.binding.ivDog)


        holder.itemView.setOnClickListener {
            val dogModel = DogModel(
                dog.id,
                dog.name,
                dog.gender,
                dog.breed,
                dog.bornDate,
                dog.shelterId,
                dog.sterilizationStatus,
                dog.rescueStory,
                dog.media
            )

            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DOG, dogModel)
            context.startActivity(intent)
        }

        Log.d(TAG, "dogPhoto: $photoDog ${dog.media}")
    }

    class ViewHolder (val binding: ItemDogBinding): RecyclerView.ViewHolder(binding.root)

    companion object {
        private val TAG = "FavoriteAdapter"
    }
}
