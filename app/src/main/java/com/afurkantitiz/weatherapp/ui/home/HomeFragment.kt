package com.afurkantitiz.weatherapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.afurkantitiz.weatherapp.base.BaseFragment
import com.afurkantitiz.weatherapp.databinding.FragmentHomeBinding
import com.afurkantitiz.weatherapp.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listeners()
    }

    private fun listeners() {
        binding.confirmButton.setOnClickListener {
            if (binding.apiKeyEdtLayout.editText?.text.toString().isNotEmpty()){
                val action = HomeFragmentDirections.actionHomeFragmentToWeatherFragment(binding.apiKeyEdtLayout.editText?.text.toString())
                findNavController().navigate(action)
            }else
                binding.apiKeyEdtLayout.error = "Api Key boş olamaz!"
        }
    }
}