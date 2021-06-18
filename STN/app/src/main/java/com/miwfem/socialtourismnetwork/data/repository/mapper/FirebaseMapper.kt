package com.miwfem.socialtourismnetwork.data.repository.mapper

import com.miwfem.socialtourismnetwork.businesslogic.model.CategoryEntity
import com.miwfem.socialtourismnetwork.businesslogic.model.MessageEntity
import com.miwfem.socialtourismnetwork.businesslogic.model.PostEntity
import com.miwfem.socialtourismnetwork.businesslogic.model.UserEntity
import com.miwfem.socialtourismnetwork.data.datasource.model.CategoryDao
import com.miwfem.socialtourismnetwork.data.datasource.model.MessageDao
import com.miwfem.socialtourismnetwork.data.datasource.model.PostDao
import com.miwfem.socialtourismnetwork.data.datasource.model.UserDao
import com.miwfem.socialtourismnetwork.data.datasource.room.model.CategoryEntityLocal

fun UserEntity.map(): UserDao = UserDao(email, name)

fun PostEntity.map(): PostDao =
    PostDao(id, user, userName, location, area, category, comment, isFav, withFav)

fun List<PostDao>.map(): List<PostEntity> = map { it.map() }

fun PostDao.map(): PostEntity =
    PostEntity(id, user, userName, location, area, category, comment, isFav, withFav)

@JvmName("mapCategoryDao")
fun List<CategoryDao>.map(): List<CategoryEntity> = map { it.map() }

fun CategoryEntity.map(): CategoryDao = CategoryDao(name)

fun CategoryDao.map(): CategoryEntity = CategoryEntity(name)

fun List<CategoryDao>.mapLocal(): List<CategoryEntityLocal> = map { it.mapLocal() }

fun CategoryDao.mapLocal(): CategoryEntityLocal = CategoryEntityLocal(name = name)

@JvmName("mapCategoryEntityRoom")
fun List<CategoryEntityLocal>.map(): List<CategoryDao> = map { it.map() }

fun CategoryEntityLocal.map(): CategoryDao = CategoryDao(name)

fun MessageEntity.map(): MessageDao = MessageDao(userEmissary, postId, userReceptor, message)

fun MessageDao.map(): MessageEntity = MessageEntity(userEmissary, postId, userReceptor, message)

@JvmName("mapMessageDao")
fun List<MessageDao>.map(): List<MessageEntity> = map { it.map() }