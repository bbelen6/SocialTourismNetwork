package com.miwfem.socialtourismnetwork.data.repository.mapper

import com.miwfem.socialtourismnetwork.businesslogic.model.LocationEntity
import com.miwfem.socialtourismnetwork.data.datasource.model.LocationDao
import com.miwfem.socialtourismnetwork.data.datasource.model.LocationsResponseDao

fun LocationsResponseDao.map(): List<LocationEntity> {
    return locations.map { it.map() }
}

fun LocationDao.map(): LocationEntity = LocationEntity(name, areaName)