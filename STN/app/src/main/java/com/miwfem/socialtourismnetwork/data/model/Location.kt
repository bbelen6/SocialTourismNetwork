package com.miwfem.socialtourismnetwork.data.model

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("densidad_por_km2")
    val densityKm2: Double,
    @SerializedName("municipio_codigo")
    val locationCode: String,
    @SerializedName("municipio_codigo_ine")
    val locationCodeIne: String,
    @SerializedName("municipio_nombre")
    val name: String,
    @SerializedName("nuts4_codigo")
    val nuts4Code: String,
    @SerializedName("nuts4_nombre")
    val nuts4Name: String,
    @SerializedName("superficie_km2")
    val areaKm2: Double
)