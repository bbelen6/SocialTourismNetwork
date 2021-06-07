package com.miwfem.socialtourismnetwork.businesslogic.usecase

import com.miwfem.socialtourismnetwork.businesslogic.model.PostEntity
import com.miwfem.socialtourismnetwork.businesslogic.repository.IFirebaseRepository
import com.miwfem.socialtourismnetwork.utils.ResultType

class ManageFavoriteUseCase(private val firebaseRepository: IFirebaseRepository) {

    fun execute(params: Params): ResultType {
        return with(params) {
            if (post.isFav) {
                firebaseRepository.addFavoritePost(post, logUser)
            } else {
                firebaseRepository.deleteFavoritePost(post, logUser)
            }
        }
    }

    data class Params(
        val post: PostEntity,
        val logUser: String?
    )
}