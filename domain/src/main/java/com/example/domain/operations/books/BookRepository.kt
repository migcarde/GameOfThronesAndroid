package com.example.domain.operations.books

import com.example.commons.data.types.Either

interface BookRepository {
    suspend fun getBooks(): Either<BooksFailure, List<BookBusiness>>
}