package com.miwfem.socialtourismnetwork.data.datasource.model

import com.google.gson.annotations.SerializedName

data class TiaResponseDao(
    @SerializedName("data")
    val tiaLocations: List<TiaLocationDao>
)