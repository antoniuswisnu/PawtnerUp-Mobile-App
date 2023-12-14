package com.example.pawtnerup.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.pawtnerup.R
import com.example.pawtnerup.api.request.PostRefreshTokenRequest
import com.example.pawtnerup.api.response.RecommendationResponse
import com.example.pawtnerup.api.response.RefreshResponse
import com.example.pawtnerup.api.retrofit.ApiConfig
import com.example.pawtnerup.data.model.TokenManager
import com.example.pawtnerup.data.repository.PetRepository
import com.example.pawtnerup.databinding.FragmentHomeBinding
import com.example.pawtnerup.ui.login.LoginActivity
import com.example.pawtnerup.ui.login.LoginViewModel
import com.example.pawtnerup.ui.login.LoginViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private  lateinit var binding: FragmentHomeBinding
    private lateinit var manager: CardStackLayoutManager
    private lateinit var list : ArrayList<RecommendationResponse>
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var account: GoogleSignInAccount? = null
    private lateinit var viewModel: HomeViewModel
    private val loginViewModel by viewModels<LoginViewModel>{
        LoginViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.pawtnerup_mobile_client_id_new))
            .requestServerAuthCode(getString(R.string.pawtnerup_mobile_client_id_new), true)
            .build()

        list = arrayListOf()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        account = GoogleSignIn.getLastSignedInAccount(requireActivity())

        postRefreshToken(account)

        val refreshToken = TokenManager.refreshTokenManager

        val apiService = ApiConfig.getApiService(requireActivity(), account?.idToken.toString(), refreshToken?:"")
        val repository = PetRepository(apiService)
        val factory = HomeViewModelFactory(repository)

        Log.d(TAG, "Refresh Token: ${LoginActivity().refreshToken}")
        Log.d(TAG, "Token: ${loginViewModel.getToken()}")
        Log.d(TAG, "refreshToken Model : $refreshToken")

        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
        observeViewModel()
        viewModel.getDog()
        init()

        return binding.root
    }

    fun postRefreshToken(account: GoogleSignInAccount?){
        val client = ApiConfig.getApiServiceGetToken(requireActivity(),  account?.idToken.toString()).postRefreshToken(
            PostRefreshTokenRequest(account?.serverAuthCode.toString())
        )
        client.enqueue(object : Callback<RefreshResponse> {
            override fun onResponse(
                call: Call<RefreshResponse>,
                response: Response<RefreshResponse>
            ) {
                if (response.isSuccessful){
                    val tempRefreshToken = response.body()?.data?.refreshToken
                    TokenManager.refreshTokenManager = tempRefreshToken.toString()
                    Log.d(TAG, "refreshToken: ${TokenManager.refreshTokenManager}")
                }
            }

            override fun onFailure(call: Call<RefreshResponse>, t: Throwable) {
                Toast.makeText(requireActivity(), t.message, Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun observeViewModel() {
        viewModel.recommendations.observe(viewLifecycleOwner) { recommendations ->
            list.addAll(recommendations)
            list.shuffle()
            binding.cardStackView.layoutManager = manager
            binding.cardStackView.adapter = HomeAdapter(requireContext(), list)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun init(){
        manager = CardStackLayoutManager(requireContext(), object : CardStackListener {
            override fun onCardDragging(direction: Direction?, ratio: Float) {
            }

            override fun onCardSwiped(direction: Direction?) {

                if (direction == Direction.Right){
                    viewModel.postLikeDog(requireActivity(), list[0].data?.get(0)?.id)
                    Toast.makeText(requireContext(), "You have liked this dog", Toast.LENGTH_SHORT).show()
                } else if (direction == Direction.Left){
                    viewModel.postDislikeDog(requireActivity(), list[0].data?.get(0)?.id)
                    Toast.makeText(requireContext(), "You have disliked this dog", Toast.LENGTH_SHORT).show()
                } else if (manager.topPosition == list.size){
                    Toast.makeText(requireContext(), "You have reached the end", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCardRewound() {
            }

            override fun onCardCanceled() {
            }

            override fun onCardAppeared(view: View?, position: Int) {
            }

            override fun onCardDisappeared(view: View?, position: Int) {
            }
        })

        manager.setVisibleCount(3)
        manager.setTranslationInterval(0.6f)
        manager.setScaleInterval(0.8f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}


