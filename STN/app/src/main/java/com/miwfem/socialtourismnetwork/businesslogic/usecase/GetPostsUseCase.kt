package com.miwfem.socialtourismnetwork.businesslogic.usecase

import com.miwfem.socialtourismnetwork.businesslogic.model.PostEntity
import com.miwfem.socialtourismnetwork.businesslogic.repository.IFirebaseRepository
import com.miwfem.socialtourismnetwork.utils.Result

class GetPostsUseCase(private val firebaseRepository: IFirebaseRepository) {
    suspend fun execute(params: Params): Result<List<PostEntity>> {
        return firebaseRepository.getPosts(params.logUser)
    }

    data class Params(
        val logUser: String?
    )
}