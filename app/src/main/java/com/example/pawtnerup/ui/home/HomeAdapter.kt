package com.example.pawtnerup.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pawtnerup.api.response.RecommendationResponse
import com.example.pawtnerup.data.model.DogRecommendationModel
import com.example.pawtnerup.databinding.ItemDogLayoutBinding
import com.example.pawtnerup.ui.detail.DetailRecommendationActivity

class HomeAdapter(val context: Context, private val list : ArrayList<RecommendationResponse>) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    inner class HomeViewHolder(val binding : ItemDogLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(ItemDogLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun getItemCount(): Int {
        return list.size
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val dog = list[position].data?.get(position)
        holder.binding.tvDogName.text = dog?.name
        holder.binding.tvBreed.text = dog?.breed
        if (dog?.gender == "FEMALE"){
            holder.binding.tvGender.text = "♀️ ${dog.gender.lowercase()}"
        } else {
            holder.binding.tvGender.text = "♂️ ${dog?.gender?.lowercase()}"
        }
        holder.binding.tvDogBio.text = dog?.rescueStory
//        holder.binding.tvDogAge.text = "${dog?.estimateAge} years old"
        holder.binding.tvLabel1.text = dog?.labels?.get(0)
        holder.binding.tvLabel2.text = dog?.labels?.get(1)
        holder.binding.tvLabel3.text = dog?.labels?.get(2)

        Glide.with(context)
            .load(list[position].data?.get(position)?.media?.get(0))
            .into(holder.binding.ivDog)

        holder.itemView.setOnClickListener {
            val dogModel = DogRecommendationModel(
                dog?.id,
                dog?.name,
                dog?.gender?.lowercase(),
                dog?.breed,
                dog?.estimateAge.toString(),
                dog?.sterilizationStatus,
                dog?.rescueStory,
                dog?.media,
                dog?.shelter?.name,
                dog?.shelter?.address,
                dog?.labels
            )

            val intent = Intent(context, DetailRecommendationActivity::class.java)
            intent.putExtra(DetailRecommendationActivity.EXTRA_RECOMMENDATION_DOG, dogModel)

            val optionsCompat: ActivityOptionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(
                    context as Activity,
                    Pair.create(holder.binding.ivDog, "iv_dog"),
                    Pair.create(holder.binding.tvDogName, "tv_dog_name"),
                    Pair.create(holder.binding.tvBreed, "tv_dog_breed"),
                    Pair.create(holder.binding.tvDogBio, "tv_dog_bio")
                )

            context.startActivity(intent, optionsCompat.toBundle())
        }

    }
}