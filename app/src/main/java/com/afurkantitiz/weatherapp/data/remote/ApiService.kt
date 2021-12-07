package com.afurkantitiz.weatherapp.data.remote

import com.afurkantitiz.weatherapp.data.entity.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("onecall?exclude=hourly,minutely&units=metric")
    suspend fun getWeather(
        @Query("lat") lat: Double?,
        @Query("lon") lon: Double?,
        @Query("appid") page: String?
    ): Response<WeatherResponse>
}