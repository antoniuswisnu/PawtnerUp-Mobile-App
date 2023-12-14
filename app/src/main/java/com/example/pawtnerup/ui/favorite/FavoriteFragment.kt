package com.example.pawtnerup.ui.favorite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pawtnerup.api.retrofit.ApiConfig
import com.example.pawtnerup.data.model.TokenManager
import com.example.pawtnerup.data.repository.PetRepository
import com.example.pawtnerup.databinding.FragmentFavoriteBinding
import com.example.pawtnerup.ui.login.LoginActivity
import com.example.pawtnerup.ui.login.LoginViewModel
import com.example.pawtnerup.ui.login.LoginViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var account: GoogleSignInAccount? = null

    private lateinit var viewModel: FavoriteViewModel
    private val loginViewModel by viewModels<LoginViewModel>{
        LoginViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        account = GoogleSignIn.getLastSignedInAccount(requireActivity())

        val refreshToken = TokenManager.refreshTokenManager

        val apiService = ApiConfig.getApiService(requireActivity(), account?.idToken.toString(), refreshToken?:"")
        val repository = PetRepository(apiService)
        val factory = FavoriteViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
        observeViewModel()
        viewModel.fetchData()
        return binding.root
    }

    private fun observeViewModel() {
        viewModel.adopterData.observe(viewLifecycleOwner) { adopterData ->
            val adapter = adopterData?.let { FavoriteAdapter(requireActivity(), it) }
            recyclerView = binding.rvFavorite
            recyclerView.layoutManager = LinearLayoutManager(requireActivity())
            recyclerView.adapter = adapter
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Log.e(TAG, error)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "FavoriteFragment"
    }
}