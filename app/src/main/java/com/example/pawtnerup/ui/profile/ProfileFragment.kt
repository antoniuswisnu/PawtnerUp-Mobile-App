package com.example.pawtnerup.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.pawtnerup.R
import com.example.pawtnerup.databinding.FragmentProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


class ProfileFragment : Fragment() {
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var binding : FragmentProfileBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        val account = GoogleSignIn.getLastSignedInAccount(requireActivity())

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

        binding.imgSettings.setOnClickListener{
            view.findNavController().navigate(R.id.action_navigation_profile_to_settingActivity)
        }

        binding.imgReport.setOnClickListener{
            view.findNavController().navigate(R.id.action_navigation_profile_to_reportActivity2)
        }
    }
}