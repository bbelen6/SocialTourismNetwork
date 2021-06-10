package com.miwfem.socialtourismnetwork.presentation.common.model

data class PostVO(
    val id: String? = null,
    val user: String,
    var userName: String,
    val location: String,
    val area: String,
    val category: String,
    val comment: String,
    var isFav: Boolean = false,
    var withFav: Long = 0
)
