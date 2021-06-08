package com.miwfem.socialtourismnetwork.data.datasource.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryEntityLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)
