package com.example.pawtnerup.ui.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pawtnerup.data.pref.UserModel
import com.example.pawtnerup.databinding.ActivityLoginBinding
import com.example.pawtnerup.ui.main.MainActivity
import com.example.pawtnerup.ui.onboarding.OnboardingActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class LoginActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("userData", MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val idToken = prefs.getString("idToken", null)
        val displayName = prefs.getString("displayName", null)
        val photoUrl = prefs.getString("photoUrl", null)

        if (email != null && idToken != null && displayName != null && photoUrl != null) {
            val userModel = UserModel(
                email,
                idToken,
                displayName,
                photoUrl
            )
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("userData", userModel)
            startActivity(intent)
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.signInButton.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)

        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            updateUI(account)
        } catch (e: ApiException) {
            Log.e(TAG, "Sign-in failed")
        }
    }

    private fun updateUI(account : GoogleSignInAccount) {
        if (account != null) {

            val email = account.email
            val idToken = account.idToken
            val displayName = account.displayName
            val photoUrl = account.photoUrl

            val userModel = UserModel(
                email,
                idToken,
                displayName,
                photoUrl.toString()
            )

            val editor = getSharedPreferences("userData", MODE_PRIVATE).edit()
            editor.putString("email", email)
            editor.putString("idToken", idToken)
            editor.putString("displayName", displayName)
            editor.putString("photoUrl", photoUrl.toString())
            editor.apply()

            val intent = Intent(this, OnboardingActivity::class.java)
            intent.putExtra("userData", userModel)
            startActivity(intent)
            Log.d(TAG, "Sign-in success: " +
                    "idToken : $idToken " +
                    "displayName : $displayName " +
                    "email : $email " +
                    "photoUrl : $photoUrl" +
                    "id" + account.id +
                    "account" + account.account +
                    "serverAuthCode" + account.serverAuthCode +
                    "grantedScopes" + account.grantedScopes
            )
        }
    }
    companion object {
        private const val RC_SIGN_IN = 1
        private const val TAG = "LoginActivity"
    }
}
