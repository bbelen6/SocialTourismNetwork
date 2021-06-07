package com.miwfem.socialtourismnetwork.businesslogic.model

data class PostEntity(
    val id: String? = null,
    val user: String,
    val location: String,
    val area: String,
    val category: String,
    val comment: String,
    val isFav: Boolean = false
)
