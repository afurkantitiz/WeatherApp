package com.afurkantitiz.weatherapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.afurkantitiz.weatherapp.base.BaseFragment
import com.afurkantitiz.weatherapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listeners()
    }

    private fun listeners() {
        binding.confirmButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToWeatherFragment(binding.apiKeyEdtLayout.editText?.text.toString())
            findNavController().navigate(action)
        }
    }
}