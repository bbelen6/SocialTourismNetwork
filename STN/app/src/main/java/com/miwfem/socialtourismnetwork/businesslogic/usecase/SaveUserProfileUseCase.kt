package com.miwfem.socialtourismnetwork.businesslogic.usecase

import com.miwfem.socialtourismnetwork.businesslogic.model.UserEntity
import com.miwfem.socialtourismnetwork.businesslogic.repository.IFirebaseRepository
import com.miwfem.socialtourismnetwork.utils.ResultType

class SaveUserProfileUseCase(private val firebaseRepository: IFirebaseRepository) {

    fun execute(params: Params): ResultType =
        firebaseRepository.saveUserProfile(params.user, params.oldUserName)

    data class Params(
        val user: UserEntity,
        val oldUserName: String? = null
    )

}