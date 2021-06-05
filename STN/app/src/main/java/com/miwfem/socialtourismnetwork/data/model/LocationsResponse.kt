package com.miwfem.socialtourismnetwork.data.model

import com.google.gson.annotations.SerializedName

data class LocationsResponse(

    @SerializedName("data")
    val locations: List<Location>

)