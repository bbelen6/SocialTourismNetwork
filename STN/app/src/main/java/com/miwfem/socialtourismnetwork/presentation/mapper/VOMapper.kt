package com.miwfem.socialtourismnetwork.presentation.mapper

import com.miwfem.socialtourismnetwork.businesslogic.model.LocationEntity
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.LocationVO

fun List<LocationEntity>.map(): List<LocationVO> = map { it.map() }

fun LocationEntity.map(): LocationVO = LocationVO(name)