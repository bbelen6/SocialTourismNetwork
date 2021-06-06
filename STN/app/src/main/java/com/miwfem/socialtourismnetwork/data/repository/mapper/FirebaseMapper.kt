package com.miwfem.socialtourismnetwork.data.repository.mapper

import com.miwfem.socialtourismnetwork.businesslogic.model.CategoryEntity
import com.miwfem.socialtourismnetwork.businesslogic.model.PostEntity
import com.miwfem.socialtourismnetwork.data.datasource.model.CategoryDao
import com.miwfem.socialtourismnetwork.data.datasource.model.PostDao


fun PostEntity.map(): PostDao = PostDao(user, location, area, category, comment)

fun List<CategoryDao>.map(): List<CategoryEntity> {
    val result = mutableListOf<CategoryEntity>()
    forEach {
        result.add(CategoryEntity(it.name))
    }
    return result
}