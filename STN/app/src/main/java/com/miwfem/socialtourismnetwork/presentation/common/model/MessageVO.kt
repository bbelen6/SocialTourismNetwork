package com.miwfem.socialtourismnetwork.presentation.common.model

data class MessageVO(
    val userEmissary: String,
    val userEmissaryEmail: String?,
    val postId: String,
    val userReceptor: String,
    val message: String,
    val post: String
)