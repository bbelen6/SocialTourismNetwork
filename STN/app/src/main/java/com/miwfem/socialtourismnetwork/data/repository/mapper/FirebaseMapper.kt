package com.miwfem.socialtourismnetwork.data.repository.mapper

import com.miwfem.socialtourismnetwork.businesslogic.model.CategoryEntity
import com.miwfem.socialtourismnetwork.businesslogic.model.PostEntity
import com.miwfem.socialtourismnetwork.data.datasource.model.CategoryDao
import com.miwfem.socialtourismnetwork.data.datasource.model.PostDao


fun PostEntity.map(): PostDao = PostDao(user, location, area, category, comment)

fun List<PostDao>.map(): List<PostEntity> {
    val result = mutableListOf<PostEntity>()
    forEach {
        result.add(PostEntity(it.user, it.location, it.area, it.category, it.comment))
    }
    return result
}

@JvmName("mapCategoryDao")
fun List<CategoryDao>.map(): List<CategoryEntity> {
    val result = mutableListOf<CategoryEntity>()
    forEach {
        result.add(CategoryEntity(it.name))
    }
    return result
}

fun CategoryEntity.map(): CategoryDao = CategoryDao(name)