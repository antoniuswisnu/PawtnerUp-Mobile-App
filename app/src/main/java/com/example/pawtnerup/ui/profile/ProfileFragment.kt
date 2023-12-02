package com.example.pawtnerup.ui.profile

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.pawtnerup.R
import com.example.pawtnerup.data.pref.UserModel
import com.example.pawtnerup.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var binding : FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        binding = FragmentProfileBinding.inflate(inflater, container, false)

//        profileViewModel.text.observe(viewLifecycleOwner) {
//            binding.tvProfile.text = it
//        }

        val userModel = if(Build.VERSION.SDK_INT >= 33){
            activity?.intent?.getParcelableExtra("userData",UserModel::class.java)
        } else {
            @Suppress
            activity?.intent?.getParcelableExtra<UserModel>("userData")
        }

        val name = userModel?.displayName
        val email = userModel?.email
        val photoUrl = userModel?.photoUrl

        binding.tvName.text = name
        binding.tvEmail.text = email
        Glide.with(this).load(photoUrl).into(binding.imgProfile)

        Log.d("ProfileFragment", "name:$name $ email: $email photoUrl: $photoUrl")

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