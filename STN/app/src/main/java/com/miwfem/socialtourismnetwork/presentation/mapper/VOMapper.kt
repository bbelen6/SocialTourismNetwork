package com.miwfem.socialtourismnetwork.presentation.mapper

import com.miwfem.socialtourismnetwork.businesslogic.model.CategoryEntity
import com.miwfem.socialtourismnetwork.businesslogic.model.LocationEntity
import com.miwfem.socialtourismnetwork.businesslogic.model.PostEntity
import com.miwfem.socialtourismnetwork.businesslogic.model.UserEntity
import com.miwfem.socialtourismnetwork.presentation.common.model.PostVO
import com.miwfem.socialtourismnetwork.presentation.common.model.UserVO
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.model.CategoryVO
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.model.LocationVO

fun UserVO.map(): UserEntity = UserEntity(email, name)

fun List<LocationEntity>.map(): List<LocationVO> = map { it.map() }

fun LocationEntity.map(): LocationVO = LocationVO(name, areaName)

fun PostVO.map(): PostEntity =
    PostEntity(id, user, userName, location, area, category, comment, isFav, withFav)

@JvmName("mapPostEntity")
fun List<PostEntity>.map(): List<PostVO> = map { it.map() }

fun PostEntity.map(): PostVO =
    PostVO(id, user, userName, location, area, category, comment, isFav, withFav)

@JvmName("mapCategoryEntity")
fun List<CategoryEntity>.map(): List<CategoryVO> = map { it.map() }

fun CategoryVO.map(): CategoryEntity = CategoryEntity(name)

fun CategoryEntity.map(): CategoryVO = CategoryVO(name)