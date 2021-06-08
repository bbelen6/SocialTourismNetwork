package com.miwfem.socialtourismnetwork.data.repository.mapper

import com.miwfem.socialtourismnetwork.businesslogic.model.CategoryEntity
import com.miwfem.socialtourismnetwork.businesslogic.model.PostEntity
import com.miwfem.socialtourismnetwork.data.datasource.model.CategoryDao
import com.miwfem.socialtourismnetwork.data.datasource.model.PostDao
import com.miwfem.socialtourismnetwork.data.datasource.room.CategoryEntityLocal


fun PostEntity.map(): PostDao = PostDao(id, user, location, area, category, comment, isFav, withFav)

fun List<PostDao>.map(): List<PostEntity> = map { it.map() }

fun PostDao.map(): PostEntity =
    PostEntity(id, user, location, area, category, comment, isFav, withFav)

@JvmName("mapCategoryDao")
fun List<CategoryDao>.map(): List<CategoryEntity> = map { it.map() }

fun CategoryEntity.map(): CategoryDao = CategoryDao(name)

fun CategoryDao.map(): CategoryEntity = CategoryEntity(name)

fun List<CategoryDao>.mapRoom(): List<CategoryEntityLocal> = map { it.mapRoom() }

fun CategoryDao.mapRoom(): CategoryEntityLocal = CategoryEntityLocal(name = name)

@JvmName("mapCategoryEntityRoom")
fun List<CategoryEntityLocal>.map(): List<CategoryDao> = map { it.map() }

fun CategoryEntityLocal.map(): CategoryDao = CategoryDao(name)