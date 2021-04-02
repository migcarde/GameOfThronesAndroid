package com.example.data.operations.books

import com.example.commons.data.types.Either
import com.example.commons_android.system.SystemInformation
import com.example.data.ParsedResponse
import com.example.domain.RepositoryFailure
import com.example.domain.operations.books.BookBusiness
import com.example.domain.operations.books.BooksFailure
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class BooksRepositoryImplTest {

    private val bookRemoteDataSource = mock(BookRemoteDataSource::class.java)
    private val systemInformation = mock(SystemInformation::class.java)

    private val bookRepository = BookRepositoryImpl(bookRemoteDataSource, systemInformation)

    val bookResponse = BookResponse(
        name = "Test",
        isbn = "Test isbn",
        authors = listOf("Test authors"),
        numberOfPages = 100,
        publisher = "Test publisher",
        country = "Test country",
        mediaType = "Test media",
        released = "Test released"
    )

    val bookBusiness = BookBusiness(
        name = "Test",
        isbn = "Test isbn",
        authors = listOf("Test authors"),
        numberOfPages = 100,
        publisher = "Test publisher",
        country = "Test country",
        mediaType = "Test media",
        released = "Test released"
    )

    @Test
    fun `Get books - Success`() = runBlocking {
        // Given
        val response = ParsedResponse.Success(listOf(bookResponse))

        `when`(systemInformation.hasConnection).thenReturn(true)
        `when`(bookRemoteDataSource.getBooks()).thenReturn(response)

        // When
        val result = bookRepository.getBooks()

        // Then
        val expectedResult = Either.Right(listOf(bookBusiness))

        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get books - Unknown`() = runBlocking {
        // Given
        val response = ParsedResponse.Failure(RepositoryFailure.Unknown)

        `when`(systemInformation.hasConnection).thenReturn(true)
        `when`(bookRemoteDataSource.getBooks()).thenReturn(response)

        // When
        val result = bookRepository.getBooks()

        // Then
        val expectedResult = Either.Left(BooksFailure.Repository(RepositoryFailure.Unknown))

        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get books - Unauthorized`() = runBlocking {
        // Given
        val response = ParsedResponse.Failure(RepositoryFailure.Unauthorized)

        `when`(systemInformation.hasConnection).thenReturn(true)
        `when`(bookRemoteDataSource.getBooks()).thenReturn(response)

        // When
        val result = bookRepository.getBooks()

        // Then
        val expectedResult = Either.Left(BooksFailure.Repository(RepositoryFailure.Unauthorized))

        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get books - Server error`() = runBlocking {
        // Given
        val response = ParsedResponse.Failure(RepositoryFailure.ServerError)

        `when`(systemInformation.hasConnection).thenReturn(true)
        `when`(bookRemoteDataSource.getBooks()).thenReturn(response)

        // When
        val result = bookRepository.getBooks()

        // Then
        val expectedResult = Either.Left(BooksFailure.Repository(RepositoryFailure.ServerError))

        Assert.assertEquals(expectedResult, result)
    }

}