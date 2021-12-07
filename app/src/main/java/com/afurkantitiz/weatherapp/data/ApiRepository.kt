package com.afurkantitiz.weatherapp.data

import com.afurkantitiz.weatherapp.data.remote.RemoteDataSource
import com.afurkantitiz.weatherapp.utils.performNetworkOperation
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private var remoteDataSource: RemoteDataSource,
) {
    fun getWeather(lat: Double?, lon: Double?, appId: String?) = performNetworkOperation {
        remoteDataSource.getWeather(lat, lon, appId)
    }
}