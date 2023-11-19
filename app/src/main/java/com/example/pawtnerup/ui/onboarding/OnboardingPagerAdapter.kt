package com.example.pawtnerup.ui.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pawtnerup.databinding.ItemOnboardingBinding

class OnboardingPagerAdapter(private val onBoardingItems: List<OnBoardingItem>) :
    RecyclerView.Adapter<OnboardingPagerAdapter.OnboardingViewHolder>() {

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.setOnBoardingData(onBoardingItems[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val binding = ItemOnboardingBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return OnboardingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return onBoardingItems.size
    }

    inner class OnboardingViewHolder(private val binding : ItemOnboardingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setOnBoardingData(onBoardingItem: OnBoardingItem) {
            binding.textTitle.text = onBoardingItem.title
            binding.textDescription.text = onBoardingItem.description
            binding.imageOnboarding.setImageResource(onBoardingItem.image)
        }
    }
}
