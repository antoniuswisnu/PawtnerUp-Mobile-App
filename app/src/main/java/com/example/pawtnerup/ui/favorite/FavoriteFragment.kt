package com.example.pawtnerup.ui.favorite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pawtnerup.R
import com.example.pawtnerup.api.response.AdopterResponse
import com.example.pawtnerup.api.retrofit.ApiConfig
import com.example.pawtnerup.databinding.FragmentFavoriteBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var account: GoogleSignInAccount? = null

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

        getData()
        return binding.root
    }

    private fun getData() {
        val client =
            ApiConfig.getApiServiceWithContext(requireActivity(), account?.idToken.toString())
                .getAdopter()
        client.enqueue(object : Callback<AdopterResponse> {
            override fun onResponse(
                call: Call<AdopterResponse>,
                response: Response<AdopterResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()?.dataAdopter

//                    val likeDog = body?.preference?.map {
//                        "LIKE"
//                    }

                    val adapter = body?.let { FavoriteAdapter(requireActivity(),it) }
                    recyclerView = binding.rvFavorite
                    recyclerView.layoutManager = LinearLayoutManager(requireActivity())
                    recyclerView.adapter = adapter

                    Log.d(TAG, "Response Body: $body ${response.isSuccessful}")
                }
            }

            override fun onFailure(call: Call<AdopterResponse>, t: Throwable) {
                t.printStackTrace()
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "FavoriteFragment"
    }
}