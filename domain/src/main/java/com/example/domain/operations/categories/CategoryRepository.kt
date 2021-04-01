package com.example.domain.operations.categories

import com.example.commons.data.types.Either

interface CategoryRepository {
    suspend fun getCategories() : Either<CategoriesFailure, CategoryBusiness>
}