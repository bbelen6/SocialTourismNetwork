package com.miwfem.socialtourismnetwork.presentation.mapper

import com.miwfem.socialtourismnetwork.businesslogic.model.CategoryEntity
import com.miwfem.socialtourismnetwork.businesslogic.model.LocationEntity
import com.miwfem.socialtourismnetwork.businesslogic.model.PostEntity
import com.miwfem.socialtourismnetwork.presentation.common.PostVO
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.model.CategoryVO
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.model.LocationVO

fun List<LocationEntity>.map(): List<LocationVO> = map { it.map() }

fun LocationEntity.map(): LocationVO = LocationVO(name, areaName)

fun PostVO.map(): PostEntity =
    PostEntity(id, user, location, area, category, comment, isFav, withFav)

@JvmName("mapPostEntity")
fun List<PostEntity>.map(): List<PostVO> = map { it.map() }

fun PostEntity.map(): PostVO = PostVO(id, user, location, area, category, comment, isFav, withFav)

@JvmName("mapCategoryEntity")
fun List<CategoryEntity>.map(): List<CategoryVO> = map { it.map() }

fun CategoryVO.map(): CategoryEntity = CategoryEntity(name)

fun CategoryEntity.map(): CategoryVO = CategoryVO(name)