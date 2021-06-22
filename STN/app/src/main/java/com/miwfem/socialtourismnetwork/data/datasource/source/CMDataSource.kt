package com.miwfem.socialtourismnetwork.data.datasource.source

import com.miwfem.socialtourismnetwork.data.datasource.api.ComunidadMadridService
import com.miwfem.socialtourismnetwork.data.datasource.model.LocationsResponseDao
import com.miwfem.socialtourismnetwork.data.datasource.model.TiaResponseDao
import com.miwfem.socialtourismnetwork.utils.Result

class CMDataSource(private val cmApiService: ComunidadMadridService) {
    suspend fun getLocations(): Result<LocationsResponseDao> {
        return try {
            Result.success(cmApiService.getLocations().body())
        } catch (exception: Exception) {
            Result.error(exception)
        }
    }

    suspend fun getTia(): Result<TiaResponseDao> {
        return try {
            Result.success(cmApiService.getTia().body())
        } catch (exception: Exception) {
            Result.error(exception)
        }
    }
}