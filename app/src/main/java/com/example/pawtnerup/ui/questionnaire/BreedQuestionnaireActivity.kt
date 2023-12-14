package com.example.pawtnerup.ui.questionnaire

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pawtnerup.api.request.PostQuestionnaireRequest
import com.example.pawtnerup.api.response.BreedItem
import com.example.pawtnerup.api.response.BreedResponse
import com.example.pawtnerup.api.response.QuestionnaireResponse
import com.example.pawtnerup.api.retrofit.ApiConfig
import com.example.pawtnerup.data.model.BreedModel
import com.example.pawtnerup.data.model.QuestionnaireModel
import com.example.pawtnerup.databinding.ActivityBreedQuestionnaireBinding
import com.example.pawtnerup.ui.splash.LoadingActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BreedQuestionnaireActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBreedQuestionnaireBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var account: GoogleSignInAccount? = null

    private var petSizes = ArrayList<String>()
    private var petAges = ArrayList<String>()
    private var petGenders = ArrayList<String>()
    private var petBreeds: Int? = null
    private var allPetBreeds = ArrayList<Int>()
    private val allData = ArrayList<Any>()

    var listBreed: ArrayList<BreedItem> = ArrayList()

    private val selectedItems = mutableListOf<BreedModel>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBreedQuestionnaireBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        account = GoogleSignIn.getLastSignedInAccount(this)

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ){
                val selectedItem = parent?.getItemAtPosition(position).toString()

                val selectedId = listBreed[position].id.toString()
                petBreeds = selectedId.toInt()

                allPetBreeds.add(petBreeds!!)
                addSelectedItem(BreedModel(selectedItem))

                Log.d(TAG, "allPetBreeds: $allPetBreeds")
                Log.d(TAG, "BreedModel : $selectedItems")

                Log.d(TAG, "onItemSelected: $selectedItem")
                Toast.makeText(this@BreedQuestionnaireActivity, selectedItem, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO()
            }
        }

        getDogBreeds()
        setupRecyclerView()

        binding.btnNext.setOnClickListener {
            postQuestionnaire()
        }
    }

    private fun getDogBreeds(){
        val questionnaireModel2 = if(Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra("questionnaireData2", QuestionnaireModel::class.java)
        } else {
            @Suppress
            intent.getParcelableExtra("questionnaireData2")
        }

        petSizes = questionnaireModel2?.petSizes!!
        val petSizeConvert = petSizes.toString().replace("[", "").replace("]", "").replace(" ","").uppercase()
        Log.d(TAG, "getDogBreeds: $petSizeConvert")

        val client = ApiConfig.getApiService(this, account?.idToken.toString(), account?.serverAuthCode.toString()).getBreeds(petSizeConvert)
        client.enqueue(object : Callback<BreedResponse> {
            override fun onResponse(
                call: Call<BreedResponse>,
                response: Response<BreedResponse>
            ) {
                if (response.isSuccessful){
                    val responseBody = response.body()
                    val dogBreeds = responseBody?.data

                    Log.d(TAG, "petSizes: $petSizes")

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            withContext(Dispatchers.Main) {
                                if (dogBreeds != null) {
                                    listBreed.clear()
                                    dogBreeds.forEach {
                                        listBreed.add(it!!)
                                    }
                                    updateSpinner(dogBreeds)
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    Log.d(TAG, "onResponse: $dogBreeds")
                }
            }
            override fun onFailure(call: Call<BreedResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun updateSpinner(data: List<BreedItem?>) {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            data.map { it?.name }
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
    }

    private fun addSelectedItem(item: BreedModel) {
        selectedItems.add(item)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun removeSelectedItem(position: Int) {
        selectedItems.removeAt(position)
        allPetBreeds.removeAt(position)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun setupRecyclerView() {
        recyclerView = binding.rvBreed
        recyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = BreedAdapter(
            selectedItems,
            onDeleteClick = { position ->
                removeSelectedItem(position)
            }
        )
    }

    private fun postQuestionnaire(){
        val questionnaireModel2 = if(Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra("questionnaireData2", QuestionnaireModel::class.java)
        } else {
            @Suppress
            intent.getParcelableExtra("questionnaireData2")
        }
        petSizes = questionnaireModel2?.petSizes!!
        petAges = questionnaireModel2.petAges!!
        petGenders = questionnaireModel2.petGenders!!

        val client = ApiConfig.getApiService(this,account?.idToken.toString(), account?.serverAuthCode.toString()).postQuestionnaire(
            PostQuestionnaireRequest(
                petGenders = petGenders.map {
                    it.uppercase()
                },
                petAges = petAges.map {
                    it.uppercase()
                },
                breedIds = allPetBreeds,
                petSizes = petSizes.map {
                    it.uppercase()
                }
            )
        )
        client.enqueue(object : Callback<QuestionnaireResponse> {
            override fun onResponse(
                call: Call<QuestionnaireResponse>,
                response: Response<QuestionnaireResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@BreedQuestionnaireActivity, "Success", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<QuestionnaireResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
        val intent = Intent(this, LoadingActivity::class.java)
        startActivity(intent)
    }

    companion object{
        private const val TAG = "BreedQuestionnaireActivity"
    }
}