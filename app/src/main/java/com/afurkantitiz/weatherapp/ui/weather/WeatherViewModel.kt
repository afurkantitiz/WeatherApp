package com.afurkantitiz.weatherapp.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.afurkantitiz.weatherapp.data.ApiRepository
import com.afurkantitiz.weatherapp.data.entity.WeatherResponse
import com.afurkantitiz.weatherapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    var apiRepository: ApiRepository
) : ViewModel() {
    fun getWeather(lat: Double?, lon: Double?, appId: String?): LiveData<Resource<WeatherResponse>> {
        return apiRepository.getWeather(lat, lon, appId)
    }
}