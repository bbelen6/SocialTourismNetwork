package com.miwfem.socialtourismnetwork.businesslogic.usecase

import com.miwfem.socialtourismnetwork.businesslogic.repository.IFirebaseRepository
import com.miwfem.socialtourismnetwork.utils.Result

class CheckExistsUserNameUseCase(private val firebaseRepository: IFirebaseRepository) {

    suspend fun execute(params: Params): Result<Boolean> {
        firebaseRepository.getUserNames().apply {
            return Result.success(data?.any { it == params.userName } ?: false)
        }
    }

    data class Params(
        val userName: String?
    )
}