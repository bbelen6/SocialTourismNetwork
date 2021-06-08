package com.miwfem.socialtourismnetwork.data.datasource.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.miwfem.socialtourismnetwork.utils.LOCATIONS_DATABASE

@Entity(tableName = LOCATIONS_DATABASE)
data class LocationEntityLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val densityKm2: Double,
    val locationCode: String,
    val locationCodeIne: String,
    val name: String,
    val areaCode: String,
    val areaName: String,
    val areaKm2: Double
)
