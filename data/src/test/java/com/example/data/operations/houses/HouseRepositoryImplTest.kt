package com.example.data.operations.houses

import com.example.commons.data.types.Either
import com.example.commons_android.system.SystemInformation
import com.example.data.ParsedResponse
import com.example.domain.RepositoryFailure
import com.example.domain.operations.houses.HouseBusiness
import com.example.domain.operations.houses.HouseFailure
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class HouseRepositoryImplTest {

    private val houseRemoteDataSource = mock(HouseRemoteDataSource::class.java)
    private val systemInformation = mock(SystemInformation::class.java)

    private val houseRepository = HouseRepositoryImpl(houseRemoteDataSource, systemInformation)

    val houseResponse = HouseResponse(
        id = "1",
        name = "Test",
        region = "Test region",
        title = "Test title"
    )

    val houseBusiness = HouseBusiness(
        id = "1",
        name = "Test",
        region = "Test region",
        title = "Test title"
    )

    @Test
    fun `Get houses - Success`() = runBlocking {
        // Given
        val response = ParsedResponse.Success(listOf(houseResponse))

        `when`(systemInformation.hasConnection).thenReturn(true)
        `when`(houseRemoteDataSource.getHouses()).thenReturn(response)

        // When
        val result = houseRepository.getHouses()

        // Then
        val expectedResult = Either.Right(listOf(houseBusiness))

        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get houses - Unknown`() = runBlocking {
        // Given
        val response = ParsedResponse.Failure(RepositoryFailure.Unknown)

        `when`(systemInformation.hasConnection).thenReturn(true)
        `when`(houseRemoteDataSource.getHouses()).thenReturn(response)

        // When
        val result = houseRepository.getHouses()

        // Then
        val expectedResult = Either.Left(HouseFailure.Repository(RepositoryFailure.Unknown))

        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get houses - Unauthorized`() = runBlocking {
        // Given
        val response = ParsedResponse.Failure(RepositoryFailure.Unauthorized)

        `when`(systemInformation.hasConnection).thenReturn(true)
        `when`(houseRemoteDataSource.getHouses()).thenReturn(response)

        // When
        val result = houseRepository.getHouses()

        // Then
        val expectedResult = Either.Left(HouseFailure.Repository(RepositoryFailure.Unauthorized))

        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get houses - Server error`() = runBlocking {
        // Given
        val response = ParsedResponse.Failure(RepositoryFailure.ServerError)

        `when`(systemInformation.hasConnection).thenReturn(true)
        `when`(houseRemoteDataSource.getHouses()).thenReturn(response)

        // When
        val result = houseRepository.getHouses()

        // Then
        val expectedResult = Either.Left(HouseFailure.Repository(RepositoryFailure.ServerError))

        Assert.assertEquals(expectedResult, result)
    }
}