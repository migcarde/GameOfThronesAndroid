package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.operations.categories.CategoryDao
import com.example.data.operations.categories.CategoryEntity

@Database(entities = [CategoryEntity::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}