package com.miwfem.socialtourismnetwork.businesslogic.usecase

import com.miwfem.socialtourismnetwork.businesslogic.model.UserEntity
import com.miwfem.socialtourismnetwork.businesslogic.repository.IFirebaseRepository
import com.miwfem.socialtourismnetwork.utils.ResultType

class SaveUserProfileUseCase(private val firebaseRepository: IFirebaseRepository) {

    fun execute(params: Params): ResultType = firebaseRepository.saveUserProfile(params.user)

    data class Params(
        val user: UserEntity
    )

}