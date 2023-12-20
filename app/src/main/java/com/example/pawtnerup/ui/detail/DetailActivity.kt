package com.example.pawtnerup.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.pawtnerup.R
import com.example.pawtnerup.api.response.PetResponse
import com.example.pawtnerup.api.retrofit.ApiConfig
import com.example.pawtnerup.data.PrefManager
import com.example.pawtnerup.data.model.DogModel
import com.example.pawtnerup.databinding.ActivityDetailBinding
import com.example.pawtnerup.ui.favorite.FavoriteFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.smarteist.autoimageslider.SliderView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var imageUrl: ArrayList<String>
    private lateinit var sliderView: SliderView
    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var petId: String
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var account: GoogleSignInAccount? = null
    private lateinit var prefManager: PrefManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        account = GoogleSignIn.getLastSignedInAccount(this)
        prefManager = PrefManager.getInstance(this)


        val dogModel = if (Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra(EXTRA_DOG, DogModel::class.java)
        } else {
            intent.getParcelableExtra<DogModel>(EXTRA_DOG)
        }

        petId = dogModel?.id.toString()
        val nameDog = dogModel?.name
        val breedDog = dogModel?.breed
        val genderDog = dogModel?.gender
        val photoDog = dogModel?.media
        val bornDate = dogModel?.bornDate
        val rescueStory = dogModel?.rescueStory
        val sterilizationStatus = dogModel?.sterilizationStatus
        val phoneNumber = dogModel?.phoneNumber
        val shelterName = dogModel?.shelterName
        val shelterAddress = dogModel?.shelterAddress
        val labels = dogModel?.labels
        phoneNumber?.replace("tel:","")?.replace("-","")

        with(binding){
            tvName.text = nameDog
            tvBreed.text = breedDog
            tvGender.text = genderDog?.lowercase()
            tvStatus.text = sterilizationStatus?.lowercase()
            tvStory.text = rescueStory
            tvAge.text = calculateAge(bornDate!!).toString()
            tvShelter.text = shelterName
            tvAddress.text = shelterAddress
            tvLabel.text = labels?.joinToString(", ")
        }

        binding.btnWa.setOnClickListener {
            if (nameDog != null) {
                contactShelter(nameDog, phoneNumber.toString())
            }
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnDelete.setOnClickListener {
            AlertDialog.Builder(this@DetailActivity)
                .setTitle("Delete")
                .setMessage("Are you sure want to delete this dog?")
                .setPositiveButton("Yes") { _, _ ->
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()

                    val yourFragment = FavoriteFragment()
                    fragmentTransaction.replace(R.id.nav_host_fragment, yourFragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()

                    deleteData()

                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        sliderView = binding.imgDetail
        imageUrl = ArrayList()

        val imageString: List<String> = listOf(
            "https://storage.googleapis.com/pawtnerup-assets/${photoDog?.get(0).toString()}",
            "https://storage.googleapis.com/pawtnerup-assets/${photoDog?.get(1).toString()}",
            "https://storage.googleapis.com/pawtnerup-assets/${photoDog?.get(2).toString()}",
        )

        sliderAdapter = SliderAdapter(imageString)
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()

        Log.d(TAG, "photoDog : $photoDog")
    }

    private fun calculateAge(birthDateString: String): Int {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val birthDate: Date = dateFormat.parse(birthDateString) ?: Date()
        val calendarBirth = Calendar.getInstance()
        calendarBirth.time = birthDate
        val calendarNow = Calendar.getInstance()
        val years = calendarNow.get(Calendar.YEAR) - calendarBirth.get(Calendar.YEAR)
        if (calendarNow.get(Calendar.DAY_OF_YEAR) < calendarBirth.get(Calendar.DAY_OF_YEAR)) {
            return years - 1
        }
        return years
    }


    private fun contactShelter(dogName: String, phoneNumber: String){
        val message = "Hi, I'm interested in adopting $dogName. Can you please provide me with more information?"

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("http://api.whatsapp.com/send?phone=$phoneNumber&text=$message")
        startActivity(intent)
    }

    private fun deleteData(){
        val client = ApiConfig.getApiService(this, account?.idToken.toString(), prefManager.getToken()).deletePet(petId)
        client.enqueue(object : Callback<PetResponse> {
            override fun onResponse(
                call: Call<PetResponse>,
                response: Response<PetResponse>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        Log.d(TAG, "onResponse: $body")
                    }
                }
            }
            override fun onFailure(call: Call<PetResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object {
        private const val TAG = "DetailActivityWisnu"
        const val EXTRA_DOG = "EXTRA_DOG"
    }
}