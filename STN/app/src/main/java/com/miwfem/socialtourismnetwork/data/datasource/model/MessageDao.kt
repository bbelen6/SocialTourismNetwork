package com.miwfem.socialtourismnetwork.data.datasource.model

data class MessageDao(
    val userEmissary: String,
    val userEmissaryEmail: String?,
    val postId: String,
    val userReceptor: String,
    val message: String,
    val post: String
)
