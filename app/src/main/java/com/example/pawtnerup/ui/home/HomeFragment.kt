package com.example.pawtnerup.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.pawtnerup.api.request.PostPreferencesRequest
import com.example.pawtnerup.api.response.PreferencesResponse
import com.example.pawtnerup.api.response.RecommendationItem
import com.example.pawtnerup.api.response.RecommendationResponse
import com.example.pawtnerup.api.retrofit.ApiConfig
import com.example.pawtnerup.databinding.FragmentHomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private  lateinit var binding: FragmentHomeBinding
    private lateinit var manager: CardStackLayoutManager
    private lateinit var list : ArrayList<RecommendationResponse>
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var account: GoogleSignInAccount? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        list = arrayListOf()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        account = GoogleSignIn.getLastSignedInAccount(requireActivity())

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
                    postLikeDog()
                    Toast.makeText(requireContext(), "You have liked this dog", Toast.LENGTH_SHORT).show()
                } else if (direction == Direction.Left){
                    postDislikeDog()
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
        val client = ApiConfig.getApiService(account?.idToken.toString()).getRecommendations()
        Log.d(TAG, "idToken: ${account?.idToken.toString()}")
        client.enqueue(object : Callback<RecommendationResponse> {
            override fun onResponse(
                call: Call<RecommendationResponse>,
                response: Response<RecommendationResponse>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    Log.d(TAG, "onResponse: $body")
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
                    Log.e(TAG, "onResponse: ${response.message()} ${response.code()} ${response.errorBody()} ${response.raw()}")
                }
                Log.d(TAG, "List: $list")

                Log.d(TAG, "onResponse: ${response.body()} ${response.code()} ${response.message()} ${response.errorBody()} ${response.isSuccessful}")
            }

            override fun onFailure(call: Call<RecommendationResponse>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun postLikeDog(){
        val client = ApiConfig.getApiServiceWithContext(requireActivity(), account?.idToken.toString()).postPreference(
            PostPreferencesRequest(
                preference = "LIKE",
//                petId = list[manager.topPosition].data?.get(manager.topPosition)?.id
                petId = list[0].data?.get(0)?.id
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

    private fun postDislikeDog(){
        val client = ApiConfig.getApiServiceWithContext(requireActivity(), account?.idToken.toString()).postPreference(
            PostPreferencesRequest(
                preference = "DISLIKE",
                petId = list[0].data?.get(0)?.id
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
