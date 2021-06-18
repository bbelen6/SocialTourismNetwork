package com.miwfem.socialtourismnetwork.businesslogic.usecase

import com.miwfem.socialtourismnetwork.businesslogic.model.MessageEntity
import com.miwfem.socialtourismnetwork.businesslogic.repository.IFirebaseRepository
import com.miwfem.socialtourismnetwork.utils.Result

class GetMessagesUseCase(private val firebaseRepository: IFirebaseRepository) {

    suspend fun execute(params: Params): Result<List<MessageEntity>> {
        return firebaseRepository.getMessages(params.logUser)
    }

    data class Params(
        val logUser: String?
    )
}