package com.miwfem.socialtourismnetwork.businesslogic.usecase

import com.miwfem.socialtourismnetwork.businesslogic.model.CategoryEntity
import com.miwfem.socialtourismnetwork.businesslogic.repository.IFirebaseRepository
import com.miwfem.socialtourismnetwork.utils.ResultType

class SaveCategoryUseCase(private val firebaseRepository: IFirebaseRepository) {
    fun execute(params: Params): ResultType = firebaseRepository.saveCategory(params.category)

    data class Params(
        val category: CategoryEntity
    )
}