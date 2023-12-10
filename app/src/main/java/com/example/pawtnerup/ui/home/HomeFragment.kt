package com.example.pawtnerup.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.pawtnerup.api.response.AdopterResponse
import com.example.pawtnerup.api.response.PetAdopter
import com.example.pawtnerup.api.retrofit.ApiConfig
import com.example.pawtnerup.data.model.DogModel
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
//    private lateinit var homeViewModel: HomeViewModel
    private lateinit var list : ArrayList<PetAdopter>
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var account: GoogleSignInAccount? = null
    private var petId: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

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
//                    homeViewModel.isLoading.observe(viewLifecycleOwner) {
//                        showLoading(it)
//                    }
//                    homeViewModel.postLikeDog(petId!!, "LIKE")
                    Toast.makeText(requireContext(), "You have liked this dog", Toast.LENGTH_SHORT).show()
                } else if (direction == Direction.Left){
//                    homeViewModel.isLoading.observe(viewLifecycleOwner) {
//                        showLoading(it)
//                    }
//                    homeViewModel.postLikeDog(petId!!, "LIKE")
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

    private fun getDog(){
        val client = ApiConfig.getApiService(account?.idToken.toString()).getAdopter()
        client.enqueue(object : Callback<AdopterResponse> {
            override fun onResponse(
                call: Call<AdopterResponse>,
                response: Response<AdopterResponse>
            ) {
                if (response.isSuccessful){
                    petId = response.body()?.dataAdopter?.preferences?.get(0)?.petId
                    val body = response.body()?.dataAdopter?.preferences
                    if (body != null){
                        for (data in body){
                            val dog = data?.pet
                            list.add(dog!!)
                        }
                        list.shuffle()
                        binding.cardStackView.layoutManager = manager
                        binding.cardStackView.adapter = HomeAdapter(requireContext(), list)
                    }

                }
            }

            override fun onFailure(call: Call<AdopterResponse>, t: Throwable) {
            }
        })
    }
}

/**
FirebaseDatabase.getInstance().getReference("users")
.addValueEventListener(object : ValueEventListener {
override fun onDataChange(snapshot: DataSnapshot) {
Log.d("HomeFragment", "onDataChange: $snapshot")
if(snapshot.exists()){
list = arrayListOf()
for(data in snapshot.children){
val dog = data.getValue(DogModel::class.java)
//                            user?.let { list.add(it) }
list.add(dog!!)
}
list.shuffle()
init()
binding.cardStackView.layoutManager = manager
binding.cardStackView.itemAnimator = DefaultItemAnimator()
binding.cardStackView.adapter = HomeAdapter(requireContext(), list)
} else {
Toast.makeText(requireContext(), "No data found", Toast.LENGTH_SHORT).show()
}
}
override fun onCancelled(error: DatabaseError) {
Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
}
})
 */