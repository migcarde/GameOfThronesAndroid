package com.example.data.operations.categories

import com.example.domain.operations.categories.CategoryBusiness

data class CategoryResponse(
    var categoryName: String,
    val type: Int
)

fun CategoryResponse.toDomain(): CategoryBusiness =
    CategoryBusiness(categoryName = this.categoryName, type = this.type)