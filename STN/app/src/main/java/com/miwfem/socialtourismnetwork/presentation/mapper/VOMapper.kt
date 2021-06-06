package com.miwfem.socialtourismnetwork.presentation.mapper

import com.miwfem.socialtourismnetwork.businesslogic.model.LocationEntity
import com.miwfem.socialtourismnetwork.businesslogic.model.PostEntity
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.model.LocationVO
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.model.PostVO

fun List<LocationEntity>.map(): List<LocationVO> = map { it.map() }

fun LocationEntity.map(): LocationVO = LocationVO(name, areaName)

fun PostVO.map(): PostEntity = PostEntity(user, location, area, category, comment)