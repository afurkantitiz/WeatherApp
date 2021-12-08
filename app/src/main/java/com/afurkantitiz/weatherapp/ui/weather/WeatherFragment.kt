package com.afurkantitiz.weatherapp.ui.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.afurkantitiz.weatherapp.R
import com.afurkantitiz.weatherapp.base.BaseFragment
import com.afurkantitiz.weatherapp.data.entity.WeatherResponse
import com.afurkantitiz.weatherapp.databinding.FragmentWeatherBinding
import com.afurkantitiz.weatherapp.utils.*
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : BaseFragment<FragmentWeatherBinding>(FragmentWeatherBinding::inflate) {
    private val viewModel: WeatherViewModel by viewModels()
    private val args: WeatherFragmentArgs by navArgs()
    private val dailyWeatherAdapter = DailyWeatherAdapter()

    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var locationManager: LocationManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationManager = requireActivity().getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
        requestPermission()
        getLocation()
        initAdapter()
        listeners()
    }

    private fun listeners() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initAdapter() {
        binding.apply {
            if(dailyWeathersRV.itemDecorationCount == 0){
                val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                itemDecoration.setDrawable(resources.getDrawable(R.drawable.layer, null))
                dailyWeathersRV.addItemDecoration(itemDecoration)
            }
            dailyWeathersRV.adapter = dailyWeatherAdapter
        }
    }

    private fun getLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                showSnackBar()
            } else {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }else{
            val lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            if (lastLocation != null){
                getWeather(lastLocation.latitude, lastLocation.longitude, args.appID)
            }
        }
    }

    private fun requestPermission() {
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                if (result) {
                    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        val lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                        if (lastLocation != null)
                            getWeather(lastLocation.latitude, lastLocation.longitude, args.appID)
                    }
                } else {
                    showSnackBar()
                }
            }
    }

    private fun showSnackBar(){
        Snackbar.make(binding.root, "Permission needed for location", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission") {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }.show()
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
                    response.data?.daily?.let { dailyWeatherAdapter.setDailyWeatherList(it) }
                }
                Resource.Status.ERROR -> {
                    if (response.message?.contains("Network error: 401") == true){
                        toast("Invalid API key. Redirecting...")
                        Handler(Looper.getMainLooper()).postDelayed({
                            findNavController().popBackStack()

                            binding.lottieLoading.gone()
                            binding.lottieLoading.cancelAnimation()
                        }, 3000)
                    }
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