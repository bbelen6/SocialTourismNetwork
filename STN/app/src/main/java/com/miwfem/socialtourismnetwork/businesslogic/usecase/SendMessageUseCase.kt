package com.miwfem.socialtourismnetwork.businesslogic.usecase

import com.miwfem.socialtourismnetwork.businesslogic.model.MessageEntity
import com.miwfem.socialtourismnetwork.businesslogic.repository.IFirebaseRepository
import com.miwfem.socialtourismnetwork.utils.ResultType

class SendMessageUseCase(private val firebaseRepository: IFirebaseRepository) {

    fun execute(params: Params): ResultType {
        return firebaseRepository.sendMessage(params.message)
    }

    data class Params(
        val message: MessageEntity
    )
}