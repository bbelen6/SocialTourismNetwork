package com.miwfem.socialtourismnetwork.data.datasource.model

data class PostDao(
    val id: String? = null,
    val user: String,
    val location: String,
    val area: String,
    val category: String,
    val comment: String
)
