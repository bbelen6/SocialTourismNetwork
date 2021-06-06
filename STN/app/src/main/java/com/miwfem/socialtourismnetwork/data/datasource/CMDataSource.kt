package com.miwfem.socialtourismnetwork.data.datasource

import com.miwfem.socialtourismnetwork.data.datasource.api.ComunidadMadridService
import com.miwfem.socialtourismnetwork.data.datasource.model.LocationsResponseDao
import com.miwfem.socialtourismnetwork.utils.Result

class CMDataSource(private val cmApiService: ComunidadMadridService) {
    suspend fun getLocations(): Result<LocationsResponseDao?> {
        return try {
            val locations = cmApiService.getLocations().body()
            Result.success(locations)
        } catch (exception: Exception) {
            Result.error(exception)
        }
    }
}