package com.example.pawtnerup.ui.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.pawtnerup.R
import com.example.pawtnerup.api.response.Story
import com.example.pawtnerup.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val story = if(Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra(EXTRA_FAVORITE, Story::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_FAVORITE)
        }

        detailViewModel.detailStory.observe(this) { story ->
            val name = story?.name
            val description = story?.description
            val photoUrl = story?.photoUrl

            try {
                Glide.with(this)
                    .load(photoUrl)
                    .into(binding.imgDetail)
                binding.tvTitle.text = name
                binding.tvDesc.text = description
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("DetailActivity", "setupView: ${e.message}")
            }
            Log.d("DetailActivity", "onCreate: $story")
        }
        detailViewModel.getDetailStories(story?.id.toString())
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_FAVORITE = "extra_story"
    }
}