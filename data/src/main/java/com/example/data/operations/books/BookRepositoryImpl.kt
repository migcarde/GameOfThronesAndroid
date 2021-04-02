package com.example.data.operations.books

import com.example.commons.data.types.Either
import com.example.commons_android.system.SystemInformation
import com.example.data.ParsedResponse
import com.example.domain.RepositoryFailure
import com.example.domain.operations.books.BookBusiness
import com.example.domain.operations.books.BookRepository
import com.example.domain.operations.books.BooksFailure
import java.lang.Exception

class BookRepositoryImpl(
    private val bookRemoteDataSource: BookRemoteDataSource,
    private val systemInformation: SystemInformation
) : BookRepository {
    override suspend fun getBooks(): Either<BooksFailure, List<BookBusiness>> {
        return when (systemInformation.hasConnection) {
            true -> {
                try {
                    when (val response = bookRemoteDataSource.getBooks()) {
                        is ParsedResponse.Success -> Either.Right(response.success.map { it.toDomain() })
                        is ParsedResponse.Failure -> Either.Left(BooksFailure.Repository(response.failure))
                        else -> Either.Left(BooksFailure.Repository(RepositoryFailure.Unknown))
                    }
                } catch (e: Exception) {
                    Either.Left(BooksFailure.Repository(RepositoryFailure.Unknown))
                }
            }
            false -> Either.Left(BooksFailure.Repository(RepositoryFailure.NoInternet))
        }
    }
}