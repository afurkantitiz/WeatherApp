package com.afurkantitiz.weatherapp.utils

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Fragment.toast(messageToShow: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(requireContext(), messageToShow, duration).show()
}