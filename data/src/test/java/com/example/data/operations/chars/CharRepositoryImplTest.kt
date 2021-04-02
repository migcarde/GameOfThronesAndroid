package com.example.data.operations.chars

import com.example.commons.data.types.Either
import com.example.commons_android.system.SystemInformation
import com.example.data.ParsedResponse
import com.example.domain.RepositoryFailure
import com.example.domain.operations.chars.CharBusiness
import com.example.domain.operations.chars.CharFailure
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class CharRepositoryImplTest {

    private val charRemoteDataSource = mock(CharRemoteDataSource::class.java)
    private val systemInformation = mock(SystemInformation::class.java)

    private val charRepository = CharRepositoryImpl(charRemoteDataSource, systemInformation)

    val charResponse = CharResponse(
        id = "1",
        name = "test",
        gender = "test gender",
        titles = emptyList(),
        aliases = emptyList(),
        allegiances = emptyList(),
        playedBy = emptyList()
    )

    val charBusiness = CharBusiness(
        id = "1",
        name = "test",
        gender = "test gender",
        titles = emptyList(),
        aliases = emptyList(),
        allegiances = emptyList(),
        playedBy = emptyList()
    )

    @Test
    fun `Get chars - Success`() = runBlocking {
        // Given
        val response = ParsedResponse.Success(listOf(charResponse))

        `when`(systemInformation.hasConnection).thenReturn(true)
        `when`(charRemoteDataSource.getChars()).thenReturn(response)

        // When
        val result = charRepository.getHouses()

        // Then
        val expectedResult = Either.Right(listOf(charBusiness))

        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get chars - Unknown`() = runBlocking {
        // Given
        val response = ParsedResponse.Failure(RepositoryFailure.Unknown)

        `when`(systemInformation.hasConnection).thenReturn(true)
        `when`(charRemoteDataSource.getChars()).thenReturn(response)

        // When
        val result = charRepository.getHouses()

        // Then
        val expectedResult = Either.Left(CharFailure.Repository(RepositoryFailure.Unknown))

        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get chars - Unauthorized`() = runBlocking {
        // Given
        val response = ParsedResponse.Failure(RepositoryFailure.Unauthorized)

        `when`(systemInformation.hasConnection).thenReturn(true)
        `when`(charRemoteDataSource.getChars()).thenReturn(response)

        // When
        val result = charRepository.getHouses()

        // Then
        val expectedResult = Either.Left(CharFailure.Repository(RepositoryFailure.Unauthorized))

        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get chars - Server error`() = runBlocking {
        // Given
        val response = ParsedResponse.Failure(RepositoryFailure.ServerError)

        `when`(systemInformation.hasConnection).thenReturn(true)
        `when`(charRemoteDataSource.getChars()).thenReturn(response)

        // When
        val result = charRepository.getHouses()

        // Then
        val expectedResult = Either.Left(CharFailure.Repository(RepositoryFailure.ServerError))

        Assert.assertEquals(expectedResult, result)
    }
}