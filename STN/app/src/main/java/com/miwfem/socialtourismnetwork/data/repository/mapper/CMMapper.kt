package com.miwfem.socialtourismnetwork.data.repository.mapper

import com.miwfem.socialtourismnetwork.businesslogic.model.LocationEntity
import com.miwfem.socialtourismnetwork.data.datasource.model.LocationDao
import com.miwfem.socialtourismnetwork.data.datasource.model.LocationsResponseDao
import com.miwfem.socialtourismnetwork.data.datasource.room.model.LocationEntityLocal

fun LocationsResponseDao.map(): List<LocationEntity> = locations.map {
    it.map()
}

fun LocationDao.map(): LocationEntity = LocationEntity(name, areaName)

fun List<LocationEntityLocal>.map(): List<LocationEntity> = map { it.map() }

fun LocationEntityLocal.map(): LocationEntity =
    LocationEntity(name, areaName)

fun LocationsResponseDao.mapLocal(): List<LocationEntityLocal> = locations.map { it.mapLocal() }

fun LocationDao.mapLocal(): LocationEntityLocal =
    LocationEntityLocal(
        densityKm2 = densityKm2,
        locationCode = locationCode,
        locationCodeIne = locationCodeIne,
        name = name,
        areaCode = areaCode,
        areaName = areaName,
        areaKm2 = areaKm2
    )