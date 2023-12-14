package com.example.pawtnerup.ui.favorite

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pawtnerup.api.response.PreferencesItem
import com.example.pawtnerup.data.model.DogModel
import com.example.pawtnerup.databinding.ItemDogBinding
import com.example.pawtnerup.ui.detail.DetailActivity

class FavoriteAdapter(val context: Context, private val listDog: List<PreferencesItem?>)
    : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listDog.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dog = listDog[position]?.pet
        val nameDog = dog?.name
        val breedDog = dog?.breed
        val photoDog = "https://storage.googleapis.com/pawtnerup-assets/${dog?.media?.get(0).toString()}"

        holder.binding.tvDogName.text = nameDog
        holder.binding.tvDogType.text = breedDog
        Glide.with(context)
            .load(photoDog)
            .override(100, 100)
            .into(holder.binding.ivDog)

        holder.itemView.setOnClickListener {
            val dogModel = DogModel(
                dog?.id,
                dog?.name,
                dog?.gender,
                dog?.breed,
                dog?.bornDate,
                dog?.shelterId,
                dog?.sterilizationStatus,
                dog?.rescueStory,
                dog?.media,
                dog?.shelter?.phoneNumber
            )

            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DOG, dogModel)

            val optionsCompat: ActivityOptionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(
                    context as Activity,
                    Pair.create(holder.binding.ivDog, "iv_dog"),
                    Pair.create(holder.binding.tvDogName, "tv_dog_name"),
                    Pair.create(holder.binding.tvDogType, "tv_dog_type")
                )

            context.startActivity(intent, optionsCompat.toBundle())
        }

        Log.d(TAG, "dogPhoto: $photoDog ${dog?.media}")
    }

    class ViewHolder (val binding: ItemDogBinding): RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val TAG = "FavoriteAdapter"
    }
}
