package com.afurkantitiz.weatherapp.data.remote

import com.afurkantitiz.weatherapp.utils.BaseDataSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : BaseDataSource() {
    suspend fun getWeather(lat: Double?, lon: Double?, appId: String?) = getResult {
        apiService.getWeather(lat, lon, appId)
    }
}