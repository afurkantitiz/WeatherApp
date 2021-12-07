package com.afurkantitiz.weatherapp.ui.weather

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.afurkantitiz.weatherapp.data.entity.Daily
import com.afurkantitiz.weatherapp.databinding.ItemDailyWeatherCardBinding
import com.afurkantitiz.weatherapp.utils.WeatherIconHelper
import com.afurkantitiz.weatherapp.utils.convertToDate
import com.bumptech.glide.Glide

class DailyWeatherAdapter: RecyclerView.Adapter<DailyWeatherAdapter.DailyWeatherViewHolder>() {
    private var dailyWeatherList: List<Daily> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): DailyWeatherAdapter.DailyWeatherViewHolder {
        val binding = ItemDailyWeatherCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyWeatherViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(
        holder: DailyWeatherAdapter.DailyWeatherViewHolder,
        position: Int
    ) {
        val dailyWeather = dailyWeatherList[position]

        holder.binding.apply {
            dayName.text = dailyWeather.dt?.let { convertToDate(it) }
            Glide
                .with(weatherIcon.context)
                .load(dailyWeather.weather?.get(0)?.icon?.let { WeatherIconHelper.getSmallIconUrl(it) })
                .into(weatherIcon)
            morningTemp.text = dailyWeather.temp?.morn?.toInt().toString() + "°"
            nightTemp.text = dailyWeather.temp?.night?.toInt().toString() + "°"
        }
    }

    override fun getItemCount(): Int = dailyWeatherList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDailyWeatherList(dailyList: List<Daily>){
        this.dailyWeatherList = dailyList
        notifyDataSetChanged()
    }

    inner class DailyWeatherViewHolder(val binding: ItemDailyWeatherCardBinding) : RecyclerView.ViewHolder(binding.root)
}