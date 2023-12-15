package com.example.pawtnerup.ui.detail

import android.R.id.message
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.pawtnerup.R
import com.example.pawtnerup.api.response.PetResponse
import com.example.pawtnerup.api.retrofit.ApiConfig
import com.example.pawtnerup.data.model.DogModel
import com.example.pawtnerup.data.model.TokenManager
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


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var imageUrl: ArrayList<String>
    private lateinit var sliderView: SliderView
    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var petId: String
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var account: GoogleSignInAccount? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        account = GoogleSignIn.getLastSignedInAccount(this)

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
        phoneNumber?.replace("tel:","")?.replace("-","")

        with(binding){
            tvName.text = nameDog
            tvBreed.text = breedDog
            tvGender.text = genderDog
            tvStatus.text = sterilizationStatus
            tvStory.text = rescueStory
            tvAge.text = bornDate
        }

        Log.d(TAG, "phoneNumber: $phoneNumber")

        binding.btnWa.setOnClickListener {
            if (nameDog != null) {
                contactShelter(nameDog, phoneNumber.toString())
            }
        }

        binding.btnDelete.setOnClickListener {
            deleteData()
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

    private fun contactShelter(dogName: String, phoneNumber: String){
        val message = "Hi, I'm interested in adopting $dogName. Can you please provide me with more information?"

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("http://api.whatsapp.com/send?phone=$phoneNumber&text=$message")
        startActivity(intent)
    }

    private fun getData(){
        val refreshToken = TokenManager.refreshTokenManager
        val client = ApiConfig.getApiService(this, account?.idToken.toString(), refreshToken?:"").getPet(petId)
        client.enqueue(object : Callback<PetResponse> {
            override fun onResponse(
                call: Call<PetResponse>,
                response: Response<PetResponse>
            ) {
                if (response.isSuccessful){
                    val responseBody = response.body()
                    val phoneNumber = responseBody?.data?.phoneNumber
                    val dogName = responseBody?.data?.name
                    val message = "Hi, I'm interested in adopting $dogName. Can you please provide me with more information?"
//                    phoneNumber?.replace("tel:","")?.replace("-","")

                    Log.d(TAG, "phoneNumber: $phoneNumber")
                    Log.d(TAG, "message: $message")

                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("http://api.whatsapp.com/send?phone=$phoneNumber&text=$message")
                    startActivity(intent)
//                    if (isWhatsAppInstalled()){
//
//                    } else {
//                        Log.d(TAG, "onResponse: WhatsApp not installed")
//                    }

                }
            }
            override fun onFailure(call: Call<PetResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun deleteData(){
        val refreshToken = TokenManager.refreshTokenManager
        val client = ApiConfig.getApiService(this, account?.idToken.toString(), refreshToken?:"").deletePet(petId)
        client.enqueue(object : Callback<PetResponse> {
            override fun onResponse(
                call: Call<PetResponse>,
                response: Response<PetResponse>
            ) {
                if (response.isSuccessful){
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


                        }
                        .setNegativeButton("No") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }
            override fun onFailure(call: Call<PetResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun isWhatsAppInstalled(): Boolean {
        val packageManager: PackageManager = packageManager
        return try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    companion object {
        private const val TAG = "DetailActivityWisnu"
        const val EXTRA_DOG = "EXTRA_DOG"
    }
}