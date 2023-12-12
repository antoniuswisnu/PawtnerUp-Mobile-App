//package com.example.pawtnerup.ui.favorite
//
//import android.app.Application
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.example.pawtnerup.api.response.AdopterResponse
//import com.example.pawtnerup.api.response.PetAdopter
//import com.example.pawtnerup.api.retrofit.ApiConfig
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class FavoriteViewModel(application: Application) : ViewModel() {
//
//    private var mGoogleSignInClient: GoogleSignInClient
//    private var account: GoogleSignInAccount? = null
//
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    private val _snackbarText = MutableLiveData<String>()
//    val snackbarText: LiveData<String> = _snackbarText
//
//    private val _listAdopter = MutableLiveData<List<PetAdopter>>()
//    val listAdopter: LiveData<List<PetAdopter>> = _listAdopter
//
//    var petId : List<Int>? = null
//
//    init {
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestEmail()
//            .build()
//
//        mGoogleSignInClient = GoogleSignIn.getClient(application.applicationContext, gso)
//        account = GoogleSignIn.getLastSignedInAccount(application.applicationContext)
//
//        getAdopter()
//    }
//
//    private fun getAdopter(){
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
//                    petId = body?.map {
//                        it?.petId!!
//                    }
//                    if (body != null){
//                        _listAdopter.value = body.map {
//                            it?.pet!!
//                        }
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<AdopterResponse>, t: Throwable) {
//                _isLoading.value = false
//                _snackbarText.value = t.localizedMessage
//            }
//        })
//    }
//}