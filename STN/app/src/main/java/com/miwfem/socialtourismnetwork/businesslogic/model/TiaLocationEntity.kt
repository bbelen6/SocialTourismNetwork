package com.miwfem.socialtourismnetwork.businesslogic.model


data class TiaLocationEntity(
    val activeVerified: Int,
    val totalVerified: Int,
    val verified: Int,
    val date: String,
    val location: String,
    val activeAccumulatedRate: Double,
    val accumulatedRateTotal: Double,
    val accumulatedRate: Double,
    var area: String? = null
)
