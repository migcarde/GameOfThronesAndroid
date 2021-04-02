package com.example.domain.operations.books

import com.example.commons.data.types.Either
import com.example.domain.base.BaseUseCase

class GetBooks(private val booksRepository: BookRepository) :
    BaseUseCase<BooksFailure, List<BookBusiness>, Unit>() {
    override suspend fun run(paramas: Unit): Either<BooksFailure, List<BookBusiness>> =
        booksRepository.getBooks()
}