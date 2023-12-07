package com.example.pawtnerup.ui.questionnaire

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.pawtnerup.api.response.PetResponse
import com.example.pawtnerup.api.response.QuestionnaireResponse
import com.example.pawtnerup.api.retrofit.ApiConfig
import com.example.pawtnerup.data.model.QuestionnaireModel
import com.example.pawtnerup.data.model.QuestionnaireModel2
import com.example.pawtnerup.databinding.ActivityBreedQuestionnaireBinding
import com.example.pawtnerup.ui.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BreedQuestionnaireActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBreedQuestionnaireBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var bio = ""
    private var petPersonality = ""
    private var petSizes = ArrayList<String>()
    private var petAges = ArrayList<String>()
    private var petGenders = ArrayList<String>()
    private var petBreeds = 0
    private val allData = ArrayList<String>()
    private var account: GoogleSignInAccount? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBreedQuestionnaireBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        account = GoogleSignIn.getLastSignedInAccount(this)

        getDogBreeds()

        binding.btnNext.setOnClickListener {
            postQuestionnaire()
        }

        binding.btnTest.setOnClickListener {
            testData()
        }
    }

    private fun getDogBreeds(){
        val client = ApiConfig.getApiService(account?.idToken.toString()).getPets()
        client.enqueue(object : Callback<PetResponse> {
            override fun onResponse(
                call: Call<PetResponse>,
                response: Response<PetResponse>
            ) {
                if (response.isSuccessful){
                    val responseBody = response.body()
                    val dogBreeds = responseBody?.data

                    // logic to get dog breeds


                    Log.d(TAG, "onResponse: $dogBreeds")
                    Toast.makeText(this@BreedQuestionnaireActivity, "$dogBreeds", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<PetResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                TODO("Not yet implemented")
            }

        })
    }

    private fun testData(){
        val questionnaireModel = if(Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra("questionnaireData", QuestionnaireModel::class.java)
        } else {
            @Suppress
            intent.getParcelableExtra<QuestionnaireModel>("questionnaireData")
        }

        val questionnaireModel2 = if(Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra("questionnaireData2", QuestionnaireModel2::class.java)
        } else {
            @Suppress
            intent.getParcelableExtra<QuestionnaireModel2>("questionnaireData2")
        }

        bio = questionnaireModel?.bio.toString()
        petPersonality = questionnaireModel?.petPersonality.toString()
        petSizes = questionnaireModel2?.petSizes!!
        petAges = questionnaireModel2.petAges!!
        petGenders = questionnaireModel2.petGenders!!

        allData.add(bio)
        allData.add(petPersonality)
        allData.addAll(petSizes)
        allData.addAll(petAges)
        allData.addAll(petGenders)

        Log.d(TAG, "testData: $allData")
        Toast.makeText(this, "$allData", Toast.LENGTH_SHORT).show()
    }

    private fun postQuestionnaire(){
        val questionnaireModel = if(Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra("questionnaireData", QuestionnaireModel::class.java)
        } else {
            @Suppress
            intent.getParcelableExtra<QuestionnaireModel>("questionnaireData")
        }

        val questionnaireModel2 = if(Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra("questionnaireData2", QuestionnaireModel2::class.java)
        } else {
            @Suppress
            intent.getParcelableExtra<QuestionnaireModel2>("questionnaireData2")
        }

        bio = questionnaireModel?.bio.toString()
        petPersonality = questionnaireModel?.petPersonality.toString()
        petSizes = questionnaireModel2?.petSizes!!
        petAges = questionnaireModel2.petAges!!
        petGenders = questionnaireModel2.petGenders!!

        val client = ApiConfig.getApiService(account?.idToken.toString()).postQuestionnaire(
            bio,
            petPersonality,
            petSizes,
            petAges,
            petGenders,
            petBreeds
        )
        client.enqueue(object : Callback<QuestionnaireResponse> {
            override fun onResponse(
                call: Call<QuestionnaireResponse>,
                response: Response<QuestionnaireResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val bio = responseBody?.bio
                    val petPersonality = responseBody?.petPersonality
                    val petSizes = responseBody?.petSizes
                    val petAges = responseBody?.petAges
                    val petGenders = responseBody?.petGenders
//                    val petBreeds = responseBody?.petBreeds

                    Log.d(TAG, "onResponse: $bio")
                }
            }

            override fun onFailure(call: Call<QuestionnaireResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    companion object{
        private const val TAG = "BreedQuestionnaireActivity"
    }
}