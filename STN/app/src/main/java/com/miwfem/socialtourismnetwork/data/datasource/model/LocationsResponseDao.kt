package com.miwfem.socialtourismnetwork.data.datasource.model

import com.google.gson.annotations.SerializedName

data class LocationsResponseDao(

    @SerializedName("data")
    val locations: List<LocationDao>

)