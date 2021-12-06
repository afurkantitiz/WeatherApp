package com.afurkantitiz.weatherapp.ui.splash

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.afurkantitiz.weatherapp.R
import com.afurkantitiz.weatherapp.base.BaseFragment
import com.afurkantitiz.weatherapp.databinding.FragmentSplashBinding

class SplashFragment: BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lottieListener()
    }

    private fun lottieListener() {
        binding.lottieAnimation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                Log.v("LottieSplashAnimation", "Started")
            }

            override fun onAnimationEnd(animation: Animator?) {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }

            override fun onAnimationCancel(animation: Animator?) {
                Log.v("LottieSplashAnimation", "Canceled")
            }

            override fun onAnimationRepeat(animation: Animator?) {
                Log.v("LottieSplashAnimation", "Repeated")
            }
        })
    }
}