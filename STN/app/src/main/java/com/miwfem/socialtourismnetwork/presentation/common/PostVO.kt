package com.miwfem.socialtourismnetwork.presentation.common

data class PostVO(
    val id: String? = null,
    val user: String,
    val location: String,
    val area: String,
    val category: String,
    val comment: String,
    val isFav: Boolean = false
)
