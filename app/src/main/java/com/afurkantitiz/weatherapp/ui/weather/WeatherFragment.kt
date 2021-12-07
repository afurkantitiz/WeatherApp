package com.afurkantitiz.weatherapp.ui.weather

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.afurkantitiz.weatherapp.base.BaseFragment
import com.afurkantitiz.weatherapp.databinding.FragmentWeatherBinding
import com.afurkantitiz.weatherapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class WeatherFragment : BaseFragment<FragmentWeatherBinding>(FragmentWeatherBinding::inflate) {
    private val viewModel: WeatherViewModel by viewModels()
    private val args: WeatherFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getWeather(33.44, -94.04, args.appID)
    }

    private fun getWeather(lat: Double?, lon: Double?, appId: String?) {
        viewModel.getWeather(lat, lon, appId).observe(viewLifecycleOwner, { response ->
            when (response.status) {
                Resource.Status.LOADING -> {
                    Log.v("weatherStatus", "Loading")
                }
                Resource.Status.SUCCESS -> {
                    Log.v("weatherStatus", response.data?.current?.feelsLike.toString())
                }
                Resource.Status.ERROR -> {
                    Log.v("weatherStatus", response.message.toString())
                }
            }
        })
    }
}