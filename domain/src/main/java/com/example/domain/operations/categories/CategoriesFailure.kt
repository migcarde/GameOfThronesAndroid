package com.example.domain.operations.categories

import com.example.domain.RepositoryFailure

sealed class CategoriesFailure {
    data class Repository(val error: RepositoryFailure) : CategoriesFailure()
    data class Know(val error: CategoriesError) : CategoriesFailure()
}