package com.miwfem.socialtourismnetwork.data.datasource.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.miwfem.socialtourismnetwork.utils.CATEGORY_DATABASE

@Entity(tableName = CATEGORY_DATABASE)
data class CategoryEntityLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)
