package com.example.pawtnerup.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pawtnerup.R
import com.example.pawtnerup.databinding.ActivityLoginBinding
import com.example.pawtnerup.ui.main.MainActivity
import com.example.pawtnerup.ui.onboarding.OnboardingActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class LoginActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var binding: ActivityLoginBinding

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

        binding.signInButton.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
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

    companion object {
        private const val RC_SIGN_IN = 1
        private const val TAG = "LoginActivity"
    }
}