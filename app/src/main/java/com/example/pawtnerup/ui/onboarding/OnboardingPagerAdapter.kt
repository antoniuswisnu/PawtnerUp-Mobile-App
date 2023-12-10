package com.example.pawtnerup.ui.onboarding

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pawtnerup.databinding.ItemOnboardingBinding

class OnboardingPagerAdapter(private val onBoardingItems: List<OnBoardingItem>) :
    RecyclerView.Adapter<OnboardingPagerAdapter.OnboardingViewHolder>() {

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.setOnBoardingData(onBoardingItems[position])
        holder.playAnimation()
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
            binding.animationView.setAnimation(onBoardingItem.image)
        }

        fun playAnimation() {
            ObjectAnimator.ofFloat(binding.animationView, View.TRANSLATION_X, -30f, 30f).apply {
                duration = 6000
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
            }.start()

            val tvTitle = ObjectAnimator.ofFloat(binding.textTitle, View.ALPHA, 1f).setDuration(400)
            val tvDescription = ObjectAnimator.ofFloat(binding.textDescription, View.ALPHA, 1f).setDuration(400)

            AnimatorSet().apply {
                playSequentially(
                    tvTitle,
                    tvDescription
                )
                startDelay = 500
            }.start()
        }
    }


}
