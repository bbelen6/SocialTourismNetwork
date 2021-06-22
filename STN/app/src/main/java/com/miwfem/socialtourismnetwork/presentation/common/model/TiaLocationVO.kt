package com.miwfem.socialtourismnetwork.presentation.common.model

data class TiaLocationVO(
    val activeVerified: Int,
    val totalVerified: Int,
    val verified: Int,
    val date: String,
    val location: String,
    val activeAccumulatedRate: Double,
    val accumulatedRateTotal: Double,
    val accumulatedRate: Double
)
