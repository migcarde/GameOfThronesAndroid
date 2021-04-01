package com.example.data.operations.categories

import com.example.domain.operations.categories.CategoryBusiness

data class CategoryResponse(
    var category_name: String,
    val type: Int
)

fun CategoryResponse.toDomain(): CategoryBusiness =
    CategoryBusiness(categoryName = this.category_name, type = this.type)