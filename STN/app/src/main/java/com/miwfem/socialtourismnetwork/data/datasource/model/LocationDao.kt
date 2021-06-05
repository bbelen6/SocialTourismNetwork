package com.miwfem.socialtourismnetwork.data.datasource.model

import com.google.gson.annotations.SerializedName

data class LocationDao(
    @SerializedName("densidad_por_km2")
    val densityKm2: Double,
    @SerializedName("municipio_codigo")
    val locationCode: String,
    @SerializedName("municipio_codigo_ine")
    val locationCodeIne: String,
    @SerializedName("municipio_nombre")
    val name: String,
    @SerializedName("nuts4_codigo")
    val areaCode: String,
    @SerializedName("nuts4_nombre")
    val areaName: String,
    @SerializedName("superficie_km2")
    val areaKm2: Double
)