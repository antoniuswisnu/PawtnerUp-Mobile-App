package com.example.pawtnerup.ui.onboarding

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.pawtnerup.R
import com.example.pawtnerup.databinding.ActivityOnboardingBinding
import com.example.pawtnerup.ui.home.HomeFragment
import com.example.pawtnerup.ui.main.MainActivity
import com.example.pawtnerup.ui.questionnaire.QuestionnaireActivity

class OnboardingActivity : AppCompatActivity() {

    private lateinit var onboardingAdapter: OnboardingPagerAdapter
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onboardingAdapter = OnboardingPagerAdapter(
            listOf(
                OnBoardingItem(
                    R.drawable.dog1,
                    "Welcome to PawtnerUp",
                    "Adopt Don’t Shop!"
                ),
                OnBoardingItem(
                    R.drawable.dog2,
                    "Make a New Friend",
                    "After this there will be a questionnaire to help us to recommend your future pet "
                ),
                OnBoardingItem(
                    R.drawable.dog3,
                    "Enjoy The App!",
                    "Adopt Don’t Shop"
                )
            )
        )
        binding.onboardingViewPager.adapter = onboardingAdapter
        setCurrentOnboardingIndicators(0)
        setOnboadingIndicator()

        binding.onboardingViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentOnboardingIndicators(position)
            }
        })

        binding.buttonOnBoardingAction.setOnClickListener{
            if (binding.onboardingViewPager.currentItem + 1 < onboardingAdapter.itemCount) {
                binding.onboardingViewPager.currentItem += binding.onboardingViewPager.currentItem
            } else {
                startActivity(Intent(this, QuestionnaireActivity::class.java))
                finish()
            }
        }
    }

    private fun setOnboadingIndicator() {
        val indicators = arrayOfNulls<ImageView>(onboardingAdapter.itemCount)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                applicationContext, R.drawable.onboarding_indicator_inactive
            ))
            indicators[i]?.layoutParams = layoutParams
            binding.layoutOnboardingIndicators.addView(indicators[i])
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setCurrentOnboardingIndicators(index: Int) {
        val childCount = binding.layoutOnboardingIndicators.childCount
        for (i in 0 until childCount) {
            val imageView = binding.layoutOnboardingIndicators.getChildAt(i) as ImageView
            if (i == index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.onboarding_indicator_active))
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.onboarding_indicator_inactive))
            }
        }
        if (index == onboardingAdapter.itemCount - 1) {
            binding.buttonOnBoardingAction.text = "Get Started"
        } else {
            binding.buttonOnBoardingAction.text = "Next"
        }
    }

//    private fun setOnboardingItem() {
//        val onBoardingItems = ArrayList<OnBoardingItem>()
//        onBoardingItems.add(
//            OnBoardingItem(
//                R.drawable.dog1,
//                "Welcome to PawtnerUp",
//                "Adopt Don’t Shop!"
//            )
//        )
//        onBoardingItems.add(
//            OnBoardingItem(
//                R.drawable.dog2,
//                "Make a New Friend",
//                "After this there will be a questionnaire to help us to recommend your future pet "
//            )
//        )
//        onBoardingItems.add(
//            OnBoardingItem(
//                R.drawable.dog3,
//                "Enjoy The App!",
//                "Adopt Don’t Shop"
//            )
//        )
//    }
//
//    val itemFastFood = OnBoardingItem(
//        R.drawable.dog1,
//        "Welcome to PawtnerUp",
//        "Adopt Don’t Shop!"
//    )
//
//    val itemPayOnline = OnBoardingItem(
//        R.drawable.dog2,
//        "Make a New Friend",
//        "After this there will be a questionnaire to help us to recommend your future pet "
//    )
//
//    val itemEatTogether = OnBoardingItem(
//        R.drawable.dog3,
//        "Enjoy The App!",
//        "Adopt Don’t Shop"
//    )
}
