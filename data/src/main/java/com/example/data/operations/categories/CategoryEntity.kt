package com.example.data.operations.categories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.operations.categories.CategoryBusiness

@Entity
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @ColumnInfo(name = "category_name") val categoryName: String,
    @ColumnInfo(name = "type") val type: Int
)

fun CategoryEntity.toDomain() = CategoryBusiness(categoryName, type)