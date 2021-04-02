package com.example.data.operations.categories

import com.example.commons.data.types.Either
import com.example.commons.json.JsonParser
import com.example.commons_android.system.SystemInformation
import com.example.data.ParsedResponse
import com.example.data.ResponseParser
import com.example.domain.RepositoryFailure
import com.example.domain.operations.categories.CategoriesFailure
import com.example.domain.operations.categories.CategoryBusiness
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class CategoryRepositoryImplTest {
    private val categoryRemoteDataSource = mock(CategoryRemoteDataSource::class.java)
    private val categoryLocalDataSource = mock(CategoryLocalDataSource::class.java)
    private val systemInformation: SystemInformation = mock(SystemInformation::class.java)

    private val categoryRepositoryImpl =
        CategoryRepositoryImpl(categoryRemoteDataSource, categoryLocalDataSource, systemInformation)

    val categoryResponse = CategoryResponse(
        category_name = "Test",
        type = 1
    )

    val categoryEntity = CategoryEntity(
        categoryName = "Test",
        type = 1
    )

    val categoryBusiness = CategoryBusiness(
        categoryName = "Test",
        type = 1
    )

    @Test
    fun `Get categories - Success`() = runBlocking {
        // Given
        `when`(systemInformation.hasConnection).thenReturn(true)

        val response = ParsedResponse.Success(listOf(categoryResponse))

        `when`(categoryRemoteDataSource.getCategories()).thenReturn(response)

        // When
        val result = categoryRepositoryImpl.getCategories()

        // Then
        val expectedResult = Either.Right(listOf(categoryBusiness))

        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get categories - Unknown`() = runBlocking {
        // Given
        `when`(systemInformation.hasConnection).thenReturn(true)

        val response = ParsedResponse.Failure(RepositoryFailure.Unknown)

        `when`(categoryRemoteDataSource.getCategories()).thenReturn(response)

        // When
        val result = categoryRepositoryImpl.getCategories()

        // Then
        val expectedResult = Either.Left(CategoriesFailure.Repository(RepositoryFailure.Unknown))

        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get categories - Unauthorized`() = runBlocking {
        // Given
        `when`(systemInformation.hasConnection).thenReturn(true)

        val response = ParsedResponse.Failure(RepositoryFailure.Unauthorized)

        `when`(categoryRemoteDataSource.getCategories()).thenReturn(response)

        // When
        val result = categoryRepositoryImpl.getCategories()

        // Then
        val expectedResult =
            Either.Left(CategoriesFailure.Repository(RepositoryFailure.Unauthorized))

        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get categories - Server error`() = runBlocking {
        // Given
        `when`(systemInformation.hasConnection).thenReturn(true)

        val response = ParsedResponse.Failure(RepositoryFailure.ServerError)

        `when`(categoryRemoteDataSource.getCategories()).thenReturn(response)

        // When
        val result = categoryRepositoryImpl.getCategories()

        // Then
        val expectedResult =
            Either.Left(CategoriesFailure.Repository(RepositoryFailure.ServerError))

        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get categories - Know error`() = runBlocking {
        // Given
        `when`(systemInformation.hasConnection).thenReturn(true)

        val response = ParsedResponse.KnownError(RepositoryFailure.Unknown)

        `when`(categoryRemoteDataSource.getCategories()).thenReturn(response)

        // When
        val result = categoryRepositoryImpl.getCategories()

        // Then
        val expectedResult = Either.Left(CategoriesFailure.Repository(RepositoryFailure.Unknown))

        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get local datasource categories - Success`() = runBlocking {
        // Given
        val response = listOf(categoryEntity)

        `when`(categoryLocalDataSource.getCategories()).thenReturn(response)

        // When
        val result = categoryRepositoryImpl.getCategories()

        // Then
        val expectedResult = Either.Right(listOf(categoryBusiness))

        Assert.assertEquals(expectedResult, result)
    }
}