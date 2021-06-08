package com.miwfem.socialtourismnetwork.data.datasource.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryDaoLocal {

    @Query("SELECT * FROM CategoryEntityLocal")
    suspend fun getAllCategories(): List<CategoryEntityLocal>

    @Insert
    suspend fun insertCategories(categories: List<CategoryEntityLocal>)

    @Query("DELETE FROM CategoryEntityLocal")
    suspend fun deleteAllCategories()

}