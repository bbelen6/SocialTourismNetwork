package com.miwfem.socialtourismnetwork.data.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CategoryEntityLocal::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDaoLocal

}