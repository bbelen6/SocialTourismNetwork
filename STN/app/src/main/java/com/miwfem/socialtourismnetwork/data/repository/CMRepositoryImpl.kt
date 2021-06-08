package com.miwfem.socialtourismnetwork.data.repository

import com.miwfem.socialtourismnetwork.businesslogic.model.LocationEntity
import com.miwfem.socialtourismnetwork.businesslogic.repository.ICMRepository
import com.miwfem.socialtourismnetwork.data.datasource.room.model.LocationDaoLocal
import com.miwfem.socialtourismnetwork.data.datasource.room.model.LocationEntityLocal
import com.miwfem.socialtourismnetwork.data.datasource.source.CMDataSource
import com.miwfem.socialtourismnetwork.data.repository.mapper.map
import com.miwfem.socialtourismnetwork.data.repository.mapper.mapLocal
import com.miwfem.socialtourismnetwork.utils.Result
import com.miwfem.socialtourismnetwork.utils.ResultType

class CMRepositoryImpl(
    private val locationsDataSource: CMDataSource,
    private val locationsDaoLocal: LocationDaoLocal
) : ICMRepository {
    override suspend fun getLocations(): Result<List<LocationEntity>> {
        val localLocations = getLocalLocations()
        if (localLocations.isNullOrEmpty()) {
            locationsDataSource.getLocations().apply {
                data?.let { locationsResponse ->
                    return when (resultType) {
                        ResultType.SUCCESS -> {
                            insertLocalLocations(locationsResponse.mapLocal())
                            Result.success(locationsResponse.map())
                        }
                        ResultType.ERROR -> {
                            Result.error(error)
                        }
                    }
                }
            }
        } else {
            return Result.success(localLocations.map())
        }

        return Result.error(Exception())
    }

    private suspend fun getLocalLocations(): List<LocationEntityLocal> {
        return locationsDaoLocal.getAllLocations()
    }

    private suspend fun insertLocalLocations(locations: List<LocationEntityLocal>) {
        locationsDaoLocal.insertLocations(locations)
    }

}