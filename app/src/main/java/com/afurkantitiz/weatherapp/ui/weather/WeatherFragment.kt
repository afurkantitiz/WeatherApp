package com.afurkantitiz.weatherapp.ui.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.afurkantitiz.weatherapp.base.BaseFragment
import com.afurkantitiz.weatherapp.data.entity.WeatherResponse
import com.afurkantitiz.weatherapp.databinding.FragmentWeatherBinding
import com.afurkantitiz.weatherapp.utils.Resource
import com.afurkantitiz.weatherapp.utils.WeatherIconHelper
import com.afurkantitiz.weatherapp.utils.gone
import com.afurkantitiz.weatherapp.utils.show
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

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
                    binding.lottieLoading.show()
                    binding.lottieLoading.playAnimation()
                    binding.weatherLayout.gone()
                }
                Resource.Status.SUCCESS -> {
                    binding.lottieLoading.cancelAnimation()
                    binding.lottieLoading.gone()
                    binding.weatherLayout.show()

                    setViews(response.data)
                }
                Resource.Status.ERROR -> {
                    binding.lottieLoading.gone()
                    Log.v("weatherStatus", response.message.toString())
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setViews(data: WeatherResponse?) {
        binding.apply {
            timeZone.text = data?.timezone
            temp.text = data?.current?.temp.toString() + "°"

            Glide
                .with(requireContext())
                .load(data?.current?.weather?.get(0)?.icon?.let { WeatherIconHelper.getBigIconUrl(it) })
                .into(binding.weatherIcon)
        }
    }
}