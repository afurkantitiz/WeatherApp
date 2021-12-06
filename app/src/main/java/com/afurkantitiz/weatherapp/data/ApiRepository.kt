package com.afurkantitiz.weatherapp.data

import com.afurkantitiz.weatherapp.data.remote.RemoteDataSource
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private var remoteDataSource: RemoteDataSource,
) {

}