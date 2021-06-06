package com.miwfem.socialtourismnetwork.businesslogic.usecase

import com.miwfem.socialtourismnetwork.businesslogic.model.PostEntity
import com.miwfem.socialtourismnetwork.businesslogic.repository.IFirebaseRepository
import com.miwfem.socialtourismnetwork.utils.ResultType

class SavePostUseCase(private val firebaseRepository: IFirebaseRepository) {
    fun execute(params: Params): ResultType = firebaseRepository.savePost(params.post)

    data class Params(
        val post: PostEntity
    )
}