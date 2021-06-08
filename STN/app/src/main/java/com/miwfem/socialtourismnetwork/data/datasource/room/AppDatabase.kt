package com.miwfem.socialtourismnetwork.data.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.miwfem.socialtourismnetwork.data.datasource.room.model.CategoryDaoLocal
import com.miwfem.socialtourismnetwork.data.datasource.room.model.CategoryEntityLocal
import com.miwfem.socialtourismnetwork.data.datasource.room.model.LocationDaoLocal
import com.miwfem.socialtourismnetwork.data.datasource.room.model.LocationEntityLocal

@Database(
    entities = [CategoryEntityLocal::class, LocationEntityLocal::class],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDaoLocal

    abstract fun locationDao(): LocationDaoLocal

}