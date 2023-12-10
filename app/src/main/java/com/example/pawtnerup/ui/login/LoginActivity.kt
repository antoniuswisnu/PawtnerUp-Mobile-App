package com.example.pawtnerup.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.pawtnerup.R
import com.example.pawtnerup.databinding.ActivityLoginBinding
import com.example.pawtnerup.ui.detail.SliderAdapter
import com.example.pawtnerup.ui.main.MainActivity
import com.example.pawtnerup.ui.onboarding.OnboardingActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.smarteist.autoimageslider.SliderView

class LoginActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sliderView: SliderView
    private lateinit var sliderAdapter: SliderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.pawtnerup_mobile_client_id_new))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val account = GoogleSignIn.getLastSignedInAccount(this)

        if (account != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        playSlider()
        playAnimation()

        binding.signInButton.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val email = account.email
                val idToken = account.idToken
                val displayName = account.displayName
                val photoUrl = account.photoUrl
                Log.d(TAG, "firebaseAuthWithGoogle:" + idToken!!)

                Log.d(TAG, "Sign-in success: " +
                        "idToken : $idToken, " +
                        "displayName : $displayName, " +
                        "email : $email, " +
                        "photoUrl : $photoUrl, " +
                        "id :" + account.id + ", " +
                        "isExpired : " + account.isExpired
                )
                val intent = Intent(this, OnboardingActivity::class.java)
                startActivity(intent)
            } catch (e: ApiException){
                Log.w(TAG, "signInResult:failed code= $e")
            }
        }
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
            startDelay = 100
        }.start()
    }


    companion object {
        private const val RC_SIGN_IN = 1
        private const val TAG = "LoginActivity"
    }
}