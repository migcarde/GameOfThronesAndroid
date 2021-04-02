package com.example.domain.operations.books

import com.example.domain.RepositoryFailure

sealed class BooksFailure {
    data class Repository(val error: RepositoryFailure) : BooksFailure()
    data class Know(val error: BooksError) : BooksFailure()
}