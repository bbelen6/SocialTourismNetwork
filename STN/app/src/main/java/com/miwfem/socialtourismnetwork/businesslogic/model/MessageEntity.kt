package com.miwfem.socialtourismnetwork.businesslogic.model

data class MessageEntity(
    val userEmissary: String,
    val postId: String,
    val userReceptor: String,
    val message: String
)