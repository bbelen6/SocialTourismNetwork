package com.miwfem.socialtourismnetwork.businesslogic.repository

import com.miwfem.socialtourismnetwork.businesslogic.model.LocationEntity
import com.miwfem.socialtourismnetwork.core.datatype.Result

interface LocationsRepository {
    suspend fun getLocations(): Result<List<LocationEntity>>
}