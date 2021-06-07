package com.miwfem.socialtourismnetwork.businesslogic.usecase

import com.miwfem.socialtourismnetwork.businesslogic.model.PostEntity
import com.miwfem.socialtourismnetwork.businesslogic.repository.IFirebaseRepository
import com.miwfem.socialtourismnetwork.utils.ResultType

class DeletePostUseCase(private val firebaseRepository: IFirebaseRepository) {

    fun execute(params: Params): ResultType {
        return firebaseRepository.deletePost(params.post)
    }

    data class Params(
        val post: PostEntity
    )

}