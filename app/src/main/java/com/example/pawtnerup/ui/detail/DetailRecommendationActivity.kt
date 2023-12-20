package com.example.pawtnerup.ui.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pawtnerup.data.model.DogRecommendationModel
import com.example.pawtnerup.databinding.ActivityDetailRecommendationBinding
import com.smarteist.autoimageslider.SliderView

class DetailRecommendationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailRecommendationBinding
    private lateinit var sliderView: SliderView
    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var imageUrl: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRecommendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dogModel = if (Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra(EXTRA_RECOMMENDATION_DOG, DogRecommendationModel::class.java)
        } else {
            intent.getParcelableExtra<DogRecommendationModel>(EXTRA_RECOMMENDATION_DOG)
        }

        val nameDog = dogModel?.name
        val breedDog = dogModel?.breed
        val genderDog = dogModel?.gender
        val photoDog = dogModel?.media
        val rescueStory = dogModel?.rescueStory
        val sterilizationStatus = dogModel?.sterilizationStatus
        val breed = dogModel?.breed
        val shelterName = dogModel?.shelterName
        val shelterAddress = dogModel?.shelterAddress
        val labels = dogModel?.labels

        with(binding){
            tvName.text = nameDog
            tvBreed.text = breedDog
            tvGender.text = genderDog
            tvStatus.text = sterilizationStatus
            tvStory.text = rescueStory
            tvBreed.text = breed
            tvShelter.text = shelterName
            tvAddress.text = shelterAddress
            tvLabel.text = labels?.joinToString(", ")
        }

        sliderView = binding.imgDetail
        imageUrl = ArrayList()

        val imageString: List<String> = listOf(
            photoDog?.get(0).toString(),
            photoDog?.get(1).toString(),
            photoDog?.get(2).toString()
        )

        sliderAdapter = SliderAdapter(imageString)
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
    companion object {
        const val EXTRA_RECOMMENDATION_DOG = "EXTRA_RECOMMENDATION_DOG"
    }
}