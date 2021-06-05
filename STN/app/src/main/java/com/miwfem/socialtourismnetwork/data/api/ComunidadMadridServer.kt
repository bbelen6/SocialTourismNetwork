package com.miwfem.socialtourismnetwork.data.api

import com.miwfem.socialtourismnetwork.data.model.LocationsResponse
import com.miwfem.socialtourismnetwork.utils.LOCATIONS
import retrofit2.Response
import retrofit2.http.GET

interface ComunidadMadridServer {

    @GET(LOCATIONS)
    suspend fun getLocations(): Response<LocationsResponse>

}