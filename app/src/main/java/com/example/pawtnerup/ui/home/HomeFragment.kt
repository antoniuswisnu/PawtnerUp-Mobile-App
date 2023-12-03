package com.example.pawtnerup.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pawtnerup.databinding.FragmentHomeBinding
import com.yuyakaido.android.cardstackview.CardStackLayoutManager


class HomeFragment : Fragment() {

    private  lateinit var binding: FragmentHomeBinding
    private lateinit var manager: CardStackLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
//        getData()
        return binding.root
    }

//    private fun init(){
//        manager = CardStackLayoutManager(requireContext(), object : CardStackListener {
//            override fun onCardDragging(direction: Direction?, ratio: Float) {
//            }
//
//            override fun onCardSwiped(direction: Direction?) {
//
//                if (direction == Direction.Right){
//                    Toast.makeText(requireContext(), "You have liked this dog", Toast.LENGTH_SHORT).show()
//                } else if (direction == Direction.Left){
//                    Toast.makeText(requireContext(), "You have disliked this dog", Toast.LENGTH_SHORT).show()
//                } else if (manager.topPosition == list.size){
//                    Toast.makeText(requireContext(), "You have reached the end", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onCardRewound() {
//            }
//
//            override fun onCardCanceled() {
//            }
//
//            override fun onCardAppeared(view: View?, position: Int) {
//            }
//
//            override fun onCardDisappeared(view: View?, position: Int) {
//            }
//
//        })
//
//        manager.setVisibleCount(3)
//        manager.setTranslationInterval(0.6f)
//        manager.setScaleInterval(0.8f)
//        manager.setSwipeThreshold(0.3f)
//        manager.setMaxDegree(20.0f)
//        manager.setDirections(Direction.HORIZONTAL)
//    }
//
//    private lateinit var list : ArrayList<DogModel>
//    private fun getData(){
//        FirebaseDatabase.getInstance().getReference("users")
//            .addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    Log.d("HomeFragment", "onDataChange: $snapshot")
//                    if(snapshot.exists()){
//                        list = arrayListOf()
//                        for(data in snapshot.children){
//                            val dog = data.getValue(DogModel::class.java)
////                            user?.let { list.add(it) }
//                             list.add(dog!!)
//                        }
//                        list.shuffle()
//                        init()
//                        binding.cardStackView.layoutManager = manager
//                        binding.cardStackView.itemAnimator = DefaultItemAnimator()
//                        binding.cardStackView.adapter = HomeAdapter(requireContext(), list)
//                    } else {
//                        Toast.makeText(requireContext(), "No data found", Toast.LENGTH_SHORT).show()
//                    }
//                }
//                override fun onCancelled(error: DatabaseError) {
//                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
//                }
//            })
//    }
}