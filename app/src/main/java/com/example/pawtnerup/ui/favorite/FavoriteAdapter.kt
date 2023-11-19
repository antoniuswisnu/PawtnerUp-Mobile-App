package com.example.pawtnerup.ui.favorite

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pawtnerup.api.response.ListStoryItem
import com.example.pawtnerup.api.response.Story
import com.example.pawtnerup.databinding.ItemDogBinding
import com.example.pawtnerup.ui.detail.DetailActivity
import androidx.core.util.Pair

class StoryAdapter : PagingDataAdapter<ListStoryItem, StoryAdapter.ListViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        return ListViewHolder(
            ItemDogBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val favorite = getItem(position)
        if (favorite != null) {
            holder.bind(data = favorite)
        }
    }

    class ListViewHolder(private val binding: ItemDogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListStoryItem) {
            with(binding){
                tvDogName.text = data.name
                tvDogType.text = data.description
                val url = data.photoUrl
                Glide.with(itemView.context)
                    .load(url)
                    .into(binding.circleImageView2)
            }

            Log.d("StoryAdapter", "bind: $data")

            itemView.setOnClickListener {
                val favorite = Story(
                    data.photoUrl,
                    data.createdAt,
                    data.name,
                    data.description,
                    data.id,
                )

                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_FAVORITE, favorite)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair.create(binding.circleImageView2, "iv_item_photo"),
                        Pair.create(binding.tvDogName, "tv_item_name"),
                        Pair.create(binding.tvDogType, "tv_detail_description")
                    )
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}