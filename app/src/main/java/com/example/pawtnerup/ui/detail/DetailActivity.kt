package com.example.pawtnerup.ui.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pawtnerup.R
import com.example.pawtnerup.data.model.DogModel
import com.example.pawtnerup.databinding.ActivityDetailBinding
import com.smarteist.autoimageslider.SliderView

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    lateinit var imageUrl: ArrayList<String>
    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dogModel = if (Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra(EXTRA_DOG, DogModel::class.java)
        } else {
            intent.getParcelableExtra<DogModel>(EXTRA_DOG)
        }

        val nameDog = dogModel?.name
        val breedDog = dogModel?.breed
        val genderDog = dogModel?.gender
        val photoDog = dogModel?.media
        val bordDate = dogModel?.bornDate
        val rescueStory = dogModel?.rescueStory
        val sterilizationStatus = dogModel?.sterilizationStatus

        with(binding){
            tvName.text = nameDog
            tvBreed.text = breedDog
            tvGender.text = genderDog
            tvStatus.text = sterilizationStatus
            tvStory.text = rescueStory

        }

        sliderView = binding.imgDetail
        imageUrl = ArrayList()
//        imageUrl = photoDog as ArrayList<String>

//            (imageUrl + photoDog?.get(0) as ArrayList<String>
//                imageUrl =
//            (imageUrl + photoDog[1] as ArrayList<String>
//                imageUrl =
//            (imageUrl + photoDog[2] as ArrayList<String>

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
    }

    companion object {
        private const val TAG = "DetailActivity"
        const val EXTRA_DOG = "EXTRA_DOG"
    }

}