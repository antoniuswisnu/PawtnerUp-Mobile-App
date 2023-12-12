//package com.example.pawtnerup.ui.home
//
//import android.app.Application
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.pawtnerup.api.response.AdopterResponse
//import com.example.pawtnerup.api.response.PetAdopter
//import com.example.pawtnerup.api.response.PreferencesResponse
//import com.example.pawtnerup.api.retrofit.ApiConfig
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import kotlinx.coroutines.launch
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class HomeViewModel(application: Application, private val petRepository: PetRepository) : ViewModel() {
//
//    private var mGoogleSignInClient: GoogleSignInClient
//    private var account: GoogleSignInAccount? = null
//
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    private val _listAdopter = MutableLiveData<List<PetAdopter>>()
//    val listAdopter: LiveData<List<PetAdopter>> = _listAdopter
//
//    private val _petId = MutableLiveData<Int>()
//    val petId: LiveData<Int> = _petId
//
//    init {
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestEmail()
//            .build()
//
//        mGoogleSignInClient = GoogleSignIn.getClient(application.applicationContext, gso)
//        account = GoogleSignIn.getLastSignedInAccount(application.applicationContext)
//
//        getDogs()
//    }
//
//    private fun getDogs(){
//        _isLoading.value = true
//        val client = ApiConfig.getApiService(account?.idToken.toString()).getAdopter()
//        client.enqueue(object : Callback<AdopterResponse>{
//            override fun onResponse(
//                call: Call<AdopterResponse>,
//                response: Response<AdopterResponse>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful){
//                    val body = response.body()?.dataAdopter?.preferences
//                    if (body != null){
//                        _listAdopter.value = body.map {
//                            it?.pet!!
//                        }
//                        _petId.value = response.body()!!.dataAdopter?.preferences?.get(0)?.petId!!
//                    }
//                } else {
//                    Log.e("HomeViewModel", "onFailure: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<AdopterResponse>, t: Throwable) {
//                _isLoading.value = false
//            }
//        })
//    }
//
//    fun postLikeDog(petId: Int, preference: String) : LiveData<PreferencesResponse> {
//        _isLoading.value = true
//        val result = MutableLiveData<PreferencesResponse>()
//        viewModelScope.launch {
//            try {
//                _isLoading.value = false
//                val response = petRepository.postPreference(petId, preference)
//                result.postValue(response)
//            } catch (e: Exception) {
//                _isLoading.value = false
//                Log.e("HomeViewModel", "onFailure: ${e.message}")
//            }
//        }
//        return result
//    }
//
//}