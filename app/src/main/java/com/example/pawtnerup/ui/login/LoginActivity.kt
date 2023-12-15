package com.example.pawtnerup.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.pawtnerup.R
import com.example.pawtnerup.api.request.PostRefreshTokenRequest
import com.example.pawtnerup.api.response.RecommendationResponse
import com.example.pawtnerup.api.response.RefreshResponse
import com.example.pawtnerup.api.retrofit.ApiConfig
import com.example.pawtnerup.data.PrefManager
import com.example.pawtnerup.data.model.TokenManager
import com.example.pawtnerup.data.model.TokenModel
import com.example.pawtnerup.data.room.TokenEntity
import com.example.pawtnerup.databinding.ActivityLoginBinding
import com.example.pawtnerup.ui.ViewModelFactory
import com.example.pawtnerup.ui.detail.SliderAdapter
import com.example.pawtnerup.ui.main.MainActivity
import com.example.pawtnerup.ui.onboarding.OnboardingActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.smarteist.autoimageslider.SliderView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sliderView: SliderView
    private lateinit var sliderAdapter: SliderAdapter
    var refreshToken: String
    private val loginViewModel by viewModels<LoginViewModel>{
        LoginViewModelFactory.getInstance(this)
    }
    private var serverAuthCodeListener: ServerAuthCodeListener? = null

//    private var tokenEntity: TokenEntity? = null
//    private lateinit var loginTokenViewModel: LoginTokenViewModel
    private lateinit var prefManager: PrefManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefManager = PrefManager.getInstance(this)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.pawtnerup_mobile_client_id_new))
            .requestEmail()
            .requestServerAuthCode(getString(R.string.pawtnerup_mobile_client_id_new), true)
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val account = GoogleSignIn.getLastSignedInAccount(this)

        if (account != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        playSlider()
        playAnimation()

//        loginTokenViewModel = obtainViewModel(this@LoginActivity)

        binding.signInButton.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

//    private fun obtainViewModel(activity: AppCompatActivity): LoginTokenViewModel {
//        val factory = ViewModelFactory.getInstance(activity.application)
//        return ViewModelProvider(activity, factory)[LoginTokenViewModel::class.java]
//    }

    interface ServerAuthCodeListener {
        fun onServerAuthCodeReceived(serverAuthCode: String)
    }

    fun setServerAuthCodeListener(listener: ServerAuthCodeListener) {
        this.serverAuthCodeListener = listener
    }

    init {
        refreshToken = ""
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val email = account.email

                val idToken = account.idToken
                val serverAuth = account.serverAuthCode

                val displayName = account.displayName
                val photoUrl = account.photoUrl

                Log.d(TAG, "idToken: " + idToken!!)
                Log.d(TAG, "serverAuth di dalam onActiviftResult: $serverAuth")

                Log.d(TAG, "Sign-in success: " +
                        "idToken : $idToken, " +
                        "displayName : $displayName, " +
                        "email : $email, " +
                        "photoUrl : $photoUrl, " +
                        "id :" + account.id + ", " +
                        "isExpired : " + account.isExpired
                )

                postRefreshToken(account)


            } catch (e: ApiException){
                Log.w(TAG, "signInResult:failed code= $e")
            }
            Log.d(TAG, "loginViewModelToken : ${loginViewModel.getToken()}")
        }
    }

    fun postRefreshToken(account: GoogleSignInAccount?){
        val client = ApiConfig.getApiServiceGetToken(this,  account?.idToken.toString()).postRefreshToken(
            PostRefreshTokenRequest(account?.serverAuthCode.toString())
        )
        client.enqueue(object : Callback<RefreshResponse> {
            override fun onResponse(
                call: Call<RefreshResponse>,
                response: Response<RefreshResponse>
            ) {
                if (response.isSuccessful){
                    val tempRefreshToken = response.body()?.data?.refreshToken
                    refreshToken = tempRefreshToken.toString()
                    TokenManager.refreshTokenManager = tempRefreshToken.toString()
                    serverAuthCodeListener?.onServerAuthCodeReceived(tempRefreshToken.toString())
//                    loginTokenViewModel.insert(tempRefreshToken.toString() as TokenEntity)
                    prefManager.saveToken(tempRefreshToken.toString())

                    val client = ApiConfig.getApiService(this@LoginActivity, account?.idToken.toString(), prefManager.getToken().toString()).getRecommendations()
                    client.enqueue(object : Callback<RecommendationResponse> {
                        override fun onResponse(
                            call: Call<RecommendationResponse>,
                            response: Response<RecommendationResponse>
                        ) {
                            if (response.isSuccessful){
                                val tokenModel = TokenModel(
                                    account?.serverAuthCode.toString()
                                )
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.putExtra("tokenModel", tokenModel)
                                startActivity(intent)
                            }
                            else {
                                val intent = Intent(this@LoginActivity, OnboardingActivity::class.java)
                                startActivity(intent)
                            }
                        }

                        override fun onFailure(call: Call<RecommendationResponse>, t: Throwable) {
                            Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
                            Log.e(TAG, "onFailure: ${t.message}")
                        }
                    })








                    Log.d(TAG, "refreshToken: ${TokenManager.refreshTokenManager}")
                }
            }

            override fun onFailure(call: Call<RefreshResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object{
        private const val RC_SIGN_IN = 1
        private const val TAG = "LoginActivity"
    }


    private fun playSlider(){
        sliderView = binding.imgBgLogin

        val image: List<Int> = listOf(
            R.drawable.bg_login,
            R.drawable.login1,
            R.drawable.login2,
            R.drawable.login3,
        )

        sliderAdapter = SliderAdapter(image)
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()
    }

    private fun playAnimation() {
        val imgLogo = ObjectAnimator.ofFloat(binding.imgLogo, View.ALPHA, 1f).setDuration(500)
        val title = ObjectAnimator.ofFloat(binding.tvTitle, View.ALPHA, 1f).setDuration(500)
        val buttonLogin = ObjectAnimator.ofFloat(binding.signInButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                imgLogo,
                title,
                buttonLogin
            )
            startDelay = 300
        }.start()
    }


}