package com.miwfem.socialtourismnetwork.businesslogic.usecase

import com.miwfem.socialtourismnetwork.businesslogic.model.TiaLocationEntity
import com.miwfem.socialtourismnetwork.businesslogic.repository.ICMRepository
import com.miwfem.socialtourismnetwork.utils.Result

class GetTiaLocationsUseCase(private val cmRepository: ICMRepository) {

    suspend fun execute(): Result<List<TiaLocationEntity>> {
        return cmRepository.getTia()
    }

}