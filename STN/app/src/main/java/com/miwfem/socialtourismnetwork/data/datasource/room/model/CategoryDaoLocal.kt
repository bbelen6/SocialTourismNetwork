package com.miwfem.socialtourismnetwork.data.datasource.room.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.miwfem.socialtourismnetwork.utils.CATEGORY_DATABASE

@Dao
interface CategoryDaoLocal {

    @Query("SELECT * FROM $CATEGORY_DATABASE")
    suspend fun getAllCategories(): List<CategoryEntityLocal>

    @Insert
    suspend fun insertCategories(categories: List<CategoryEntityLocal>)

    @Query("DELETE FROM $CATEGORY_DATABASE")
    suspend fun deleteAllCategories()

}