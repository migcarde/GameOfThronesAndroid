package com.example.data.operations.categories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categoryentity")
    fun getAll(): List<CategoryEntity>

    @Insert
    fun insertAll(categories: List<CategoryEntity>)
}