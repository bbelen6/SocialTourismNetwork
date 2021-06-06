package com.miwfem.socialtourismnetwork.data.repository

import com.miwfem.socialtourismnetwork.businesslogic.model.LocationEntity
import com.miwfem.socialtourismnetwork.businesslogic.repository.LocationsRepository
import com.miwfem.socialtourismnetwork.data.datasource.LocationDataSource
import com.miwfem.socialtourismnetwork.data.repository.mapper.map
import com.miwfem.socialtourismnetwork.utils.Result
import com.miwfem.socialtourismnetwork.utils.ResultType

class LocationsRepositoryImpl(
    private val locationsDataSource: LocationDataSource
) : LocationsRepository {
    override suspend fun getLocations(): Result<List<LocationEntity>> {
        locationsDataSource.getLocations().apply {
            data?.let { locationsResponse ->
                return when (resultType) {
                    ResultType.SUCCESS -> {
                        Result.success(locationsResponse.map())
                    }
                    ResultType.ERROR -> {
                        Result.error(error)
                    }
                }
            }
        }
        return Result.error(Exception())
    }
}