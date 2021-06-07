package com.miwfem.socialtourismnetwork.data.repository.mapper

import com.miwfem.socialtourismnetwork.businesslogic.model.CategoryEntity
import com.miwfem.socialtourismnetwork.businesslogic.model.PostEntity
import com.miwfem.socialtourismnetwork.data.datasource.model.CategoryDao
import com.miwfem.socialtourismnetwork.data.datasource.model.PostDao


fun PostEntity.map(): PostDao = PostDao(id, user, location, area, category, comment, isFav, withFav)

fun List<PostDao>.map(): List<PostEntity> = map { it.map() }

fun PostDao.map(): PostEntity =
    PostEntity(id, user, location, area, category, comment, isFav, withFav)

@JvmName("mapCategoryDao")
fun List<CategoryDao>.map(): List<CategoryEntity> = map { it.map() }

fun CategoryEntity.map(): CategoryDao = CategoryDao(name)

fun CategoryDao.map(): CategoryEntity = CategoryEntity(name)