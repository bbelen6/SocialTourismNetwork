package com.miwfem.socialtourismnetwork.presentation.mapper

import com.miwfem.socialtourismnetwork.businesslogic.model.CategoryEntity
import com.miwfem.socialtourismnetwork.businesslogic.model.LocationEntity
import com.miwfem.socialtourismnetwork.businesslogic.model.PostEntity
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.model.CategoryVO
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.model.LocationVO
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.model.PostVO

fun List<LocationEntity>.map(): List<LocationVO> = map { it.map() }

fun LocationEntity.map(): LocationVO = LocationVO(name, areaName)

fun PostVO.map(): PostEntity = PostEntity(user, location, area, category, comment)

@JvmName("mapCategoryEntity")
fun List<CategoryEntity>.map(): List<CategoryVO> {
    val result = mutableListOf<CategoryVO>()
    forEach {
        result.add(CategoryVO(it.name))
    }
    return result
}

fun CategoryVO.map(): CategoryEntity = CategoryEntity(name)