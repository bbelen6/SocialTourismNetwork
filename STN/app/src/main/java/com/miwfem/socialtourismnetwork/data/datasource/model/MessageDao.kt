package com.miwfem.socialtourismnetwork.data.datasource.model

data class MessageDao(
    val userEmissary: String,
    val postId: String,
    val userReceptor: String,
    val message: String
)
