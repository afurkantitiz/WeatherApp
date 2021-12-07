package com.afurkantitiz.weatherapp.utils

class WeatherIconHelper {
    companion object{
        fun getSmallIconUrl(shortUrl: String): String{
            return "http://openweathermap.org/img/wn/$shortUrl@2x.png"
        }

        fun getBigIconUrl(shortUrl: String): String{
            return "http://openweathermap.org/img/wn/$shortUrl@4x.png"
        }
    }
}