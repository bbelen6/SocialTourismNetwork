package com.miwfem.socialtourismnetwork.businesslogic.usecase

import com.miwfem.socialtourismnetwork.businesslogic.model.LocationEntity
import com.miwfem.socialtourismnetwork.businesslogic.repository.ICMRepository
import com.miwfem.socialtourismnetwork.utils.Result

class GetLocationsUseCase(private val ICMRepository: ICMRepository) {

    suspend fun execute(): Result<List<LocationEntity>> {
        return ICMRepository.getLocations()
    }

}