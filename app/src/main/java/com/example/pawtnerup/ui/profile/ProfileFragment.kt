package com.example.pawtnerup.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.pawtnerup.databinding.FragmentProfileBinding
import com.example.pawtnerup.ui.report.ReportActivity


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

        profileViewModel.text.observe(viewLifecycleOwner) {
            binding.tvProfile.text = it
        }

        binding.imgReport.setOnClickListener{
            startActivity(Intent(context, ReportActivity::class.java))
        }

        return binding.root
    }
}