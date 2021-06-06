package com.miwfem.socialtourismnetwork.businesslogic.usecase

import com.miwfem.socialtourismnetwork.businesslogic.model.PostEntity
import com.miwfem.socialtourismnetwork.businesslogic.repository.IFirebaseRepository
import com.miwfem.socialtourismnetwork.utils.Result

class GetPostsUseCase(private val firebaseRepository: IFirebaseRepository) {
    suspend fun execute(): Result<List<PostEntity>> {
        return firebaseRepository.getPosts()
    }
}