package com.miwfem.socialtourismnetwork.data.datasource.room.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.miwfem.socialtourismnetwork.utils.LOCATIONS_DATABASE

@Dao
interface LocationDaoLocal {

    @Query("SELECT * FROM $LOCATIONS_DATABASE")
    suspend fun getAllLocations(): List<LocationEntityLocal>

    @Insert
    suspend fun insertLocations(locations: List<LocationEntityLocal>)

    @Query("DELETE FROM $LOCATIONS_DATABASE")
    suspend fun deleteAllLocations()

}