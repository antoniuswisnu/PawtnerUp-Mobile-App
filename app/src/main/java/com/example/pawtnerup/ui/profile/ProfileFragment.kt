package com.example.pawtnerup.ui.profile

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.pawtnerup.data.PrefManager
import com.example.pawtnerup.databinding.FragmentProfileBinding
import com.example.pawtnerup.ui.login.LoginActivity
import com.example.pawtnerup.ui.report.ReportActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var prefManager: PrefManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        val account = GoogleSignIn.getLastSignedInAccount(requireActivity())
        prefManager = PrefManager.getInstance(requireActivity())


        if(account != null){
            val name = account.displayName
            val email = account.email
            val photoUrl = account.photoUrl

            binding.tvName.text = name
            binding.tvEmail.text = email
            Glide.with(this)
                .load(photoUrl)
                .into(binding.imgProfile)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.csLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        binding.csNotif.setOnClickListener {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }

        binding.csAbout.setOnClickListener {
            startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS))
        }

        binding.csPrivacy.setOnClickListener {
            startActivity(Intent(Settings.ACTION_PRIVACY_SETTINGS))
        }

        binding.csLogout.setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener {
                prefManager.clear()
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finish()
            }
        }
    }
}