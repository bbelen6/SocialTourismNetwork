package com.miwfem.socialtourismnetwork.businesslogic.repository

import com.miwfem.socialtourismnetwork.businesslogic.model.LocationEntity
import com.miwfem.socialtourismnetwork.businesslogic.model.TiaLocationEntity
import com.miwfem.socialtourismnetwork.utils.Result

interface ICMRepository {

    suspend fun getLocations(): Result<List<LocationEntity>>
    suspend fun getTia(): Result<List<TiaLocationEntity>>

}