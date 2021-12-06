package com.afurkantitiz.weatherapp.ui.home

import android.os.Bundle
import android.view.View
import com.afurkantitiz.weatherapp.base.BaseFragment
import com.afurkantitiz.weatherapp.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listeners()
    }

    private fun listeners() {
        binding.confirmButton.setOnClickListener {
            TODO() //Navigate to weather screen
        }
    }
}