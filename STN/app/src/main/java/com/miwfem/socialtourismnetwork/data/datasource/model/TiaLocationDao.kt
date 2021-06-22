package com.miwfem.socialtourismnetwork.data.datasource.model

import com.google.gson.annotations.SerializedName

data class TiaLocationDao(
    @SerializedName("casos_confirmados_activos_ultimos_14dias")
    val activeVerified: Int,
    @SerializedName("casos_confirmados_totales")
    val totalVerified: Int,
    @SerializedName("casos_confirmados_ultimos_14dias")
    val verified: Int,
    @SerializedName("codigo_geometria")
    val codeGeometric: String,
    @SerializedName("fecha_informe")
    val date: String,
    @SerializedName("municipio_distrito")
    val location: String,
    @SerializedName("tasa_incidencia_acumulada_activos_ultimos_14dias")
    val activeAccumulatedRate: Double,
    @SerializedName("tasa_incidencia_acumulada_total")
    val accumulatedRateTotal: Double,
    @SerializedName("tasa_incidencia_acumulada_ultimos_14dias")
    val accumulatedRate: Double
)