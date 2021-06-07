package com.miwfem.socialtourismnetwork.businesslogic.usecase

import com.miwfem.socialtourismnetwork.businesslogic.model.PostEntity
import com.miwfem.socialtourismnetwork.businesslogic.repository.IFirebaseRepository

class DeletePostUseCase(private val firebaseRepository: IFirebaseRepository) {

    fun execute(params: Params) {
        firebaseRepository.deletePost(params.post)
    }

    data class Params(
        val post: PostEntity
    )

}