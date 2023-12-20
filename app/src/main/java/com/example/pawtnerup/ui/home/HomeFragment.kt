package com.example.pawtnerup.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pawtnerup.R
import com.example.pawtnerup.api.request.PostPreferencesRequest
import com.example.pawtnerup.api.response.PreferencesResponse
import com.example.pawtnerup.api.response.RecommendationResponse
import com.example.pawtnerup.api.retrofit.ApiConfig
import com.example.pawtnerup.data.PrefManager
import com.example.pawtnerup.databinding.FragmentHomeBinding
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
    private lateinit var prefManager: PrefManager
    private var account: GoogleSignInAccount? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.pawtnerup_mobile_client_id_new))
            .build()

        prefManager = PrefManager.getInstance(requireActivity())

        list = arrayListOf()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        account = GoogleSignIn.getLastSignedInAccount(requireActivity())

        Log.d(TAG,"list dog on create : $list")
        getDog()
        init()

        return binding.root
    }

    private fun init(){
        manager = CardStackLayoutManager(requireContext(), object : CardStackListener {
            override fun onCardDragging(direction: Direction?, ratio: Float) {
            }

            override fun onCardSwiped(direction: Direction?) {

                if (direction == Direction.Right){
                    postLikeDog(account = account)
                    Toast.makeText(requireContext(), "You have liked this dog", Toast.LENGTH_SHORT).show()
                } else if (direction == Direction.Left){
                    postDislikeDog(account = account)
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

    private fun getDog(){
        val client = ApiConfig.getApiService(requireActivity(), account?.idToken.toString(), prefManager.getToken().toString()).getRecommendations()
        client.enqueue(object : Callback<RecommendationResponse> {
            override fun onResponse(
                call: Call<RecommendationResponse>,
                response: Response<RecommendationResponse>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        for (i in 0 until body.data?.size!!){
                            list.add(body)
                        }
                        list.shuffle()
                        binding.cardStackView.layoutManager = manager
                        binding.cardStackView.adapter = HomeAdapter(requireContext(), list)
                    }
                } else{
                    Toast.makeText(requireContext(), "No data found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RecommendationResponse>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun postLikeDog(account: GoogleSignInAccount?){
        val client = ApiConfig.getApiService(requireActivity(), account?.idToken.toString(), prefManager.getToken().toString()).postPreference(
            PostPreferencesRequest(
                preference = "LIKE",
                petId = list[manager.topPosition - 1].data?.get(manager.topPosition - 1)?.id
            )
        )
        client.enqueue(object : Callback<PreferencesResponse>{
            override fun onResponse(
                call: Call<PreferencesResponse>,
                response: Response<PreferencesResponse>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    Log.d(TAG, "onResponse: $body")
                    if (body != null){
                        Toast.makeText(requireContext(), "You have liked this dog", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<PreferencesResponse>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun postDislikeDog(account: GoogleSignInAccount?){
        val client = ApiConfig.getApiService(requireActivity(), account?.idToken.toString(), prefManager.getToken().toString()).postPreference(
            PostPreferencesRequest(
                preference = "DISLIKE",
                petId = list[manager.topPosition - 1].data?.get(manager.topPosition - 1)?.id
            )
        )
        client.enqueue(object : Callback<PreferencesResponse>{
            override fun onResponse(
                call: Call<PreferencesResponse>,
                response: Response<PreferencesResponse>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    Log.d(TAG, "onResponse: $body")
                    if (body != null){
                        Toast.makeText(requireContext(), "You have disliked this dog", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<PreferencesResponse>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }
    companion object {
        private const val TAG = "HomeFragment"
    }
}


