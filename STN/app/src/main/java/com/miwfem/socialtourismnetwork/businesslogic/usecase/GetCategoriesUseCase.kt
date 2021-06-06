package com.miwfem.socialtourismnetwork.businesslogic.usecase

import com.miwfem.socialtourismnetwork.businesslogic.model.CategoryEntity
import com.miwfem.socialtourismnetwork.businesslogic.repository.IFirebaseRepository
import com.miwfem.socialtourismnetwork.utils.Result

class GetCategoriesUseCase(private val firebaseRepository: IFirebaseRepository) {
    suspend fun execute(): Result<List<CategoryEntity>> {
        return firebaseRepository.getCategories()
    }
}