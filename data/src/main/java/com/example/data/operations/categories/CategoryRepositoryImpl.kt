package com.example.data.operations.categories

import com.example.commons.data.types.Either
import com.example.commons_android.system.SystemInformation
import com.example.data.ParsedResponse
import com.example.domain.RepositoryFailure
import com.example.domain.operations.categories.CategoriesFailure
import com.example.domain.operations.categories.CategoryBusiness
import com.example.domain.operations.categories.CategoryRepository
import java.lang.Exception

class CategoryRepositoryImpl(private val categoryRemoteDataSource: CategoryRemoteDataSource, private val systemInformation: SystemInformation) : CategoryRepository {
    override suspend fun getCategories(): Either<CategoriesFailure, CategoryBusiness> {
        return when(systemInformation.hasConnection) {
            true -> {
                try {
                    when (val response = categoryRemoteDataSource.getCategories()) {
                        is ParsedResponse.Success -> Either.Right(response.success.toDomain())
                        is ParsedResponse.Failure -> Either.Left(CategoriesFailure.Repository(response.failure))
                        else -> Either.Left(CategoriesFailure.Repository(RepositoryFailure.Unknown))
                    }
                } catch (e: Exception) {
                    Either.Left(CategoriesFailure.Repository(RepositoryFailure.Unknown))
                }
            }
            false -> Either.Left(CategoriesFailure.Repository(RepositoryFailure.NoInternet))
        }
    }
}