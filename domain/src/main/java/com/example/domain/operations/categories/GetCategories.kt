package com.example.domain.operations.categories

import com.example.commons.data.types.Either
import com.example.domain.base.BaseUseCase

class
GetCategories(private val categoriesRepository: CategoryRepository) :
    BaseUseCase<CategoriesFailure, List<CategoryBusiness>, Unit>() {
    override suspend fun run(paramas: Unit): Either<CategoriesFailure, List<CategoryBusiness>> =
        categoriesRepository.getCategories()
}