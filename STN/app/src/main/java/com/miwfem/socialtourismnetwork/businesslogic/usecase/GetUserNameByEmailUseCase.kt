package com.miwfem.socialtourismnetwork.businesslogic.usecase

import com.miwfem.socialtourismnetwork.businesslogic.repository.IFirebaseRepository

class GetUserNameByEmailUseCase(private val firebaseRepository: IFirebaseRepository) {

    suspend fun execute(params: Params) = firebaseRepository.getUserNameByEmail(params.email)

    data class Params(
        val email: String
    )
}