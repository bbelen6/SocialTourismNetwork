package com.miwfem.socialtourismnetwork.businesslogic.usecase

import com.miwfem.socialtourismnetwork.businesslogic.model.LocationEntity
import com.miwfem.socialtourismnetwork.businesslogic.repository.LocationsRepository
import com.miwfem.socialtourismnetwork.core.datatype.Result

class LocationsUseCase(private val locationsRepository: LocationsRepository) {

    suspend fun execute(): Result<List<LocationEntity>> {
        return locationsRepository.getLocations()
    }

}