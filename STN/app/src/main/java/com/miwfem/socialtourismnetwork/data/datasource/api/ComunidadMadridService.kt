package com.miwfem.socialtourismnetwork.data.datasource.api

import com.miwfem.socialtourismnetwork.data.datasource.model.LocationsResponseDao
import com.miwfem.socialtourismnetwork.utils.LOCATIONS
import retrofit2.Response
import retrofit2.http.GET

interface ComunidadMadridService {

    @GET(LOCATIONS)
    suspend fun getLocations(): Response<LocationsResponseDao>

}