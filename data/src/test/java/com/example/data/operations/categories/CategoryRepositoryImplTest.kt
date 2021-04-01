package com.example.data.operations.categories

import com.example.commons.data.types.Either
import com.example.commons.json.JsonParser
import com.example.commons_android.system.SystemInformation
import com.example.data.ParsedResponse
import com.example.data.ResponseParser
import com.example.domain.operations.categories.CategoryBusiness
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class CategoryRepositoryImplTest {
    private val categoryRemoteDataSource = mock(CategoryRemoteDataSource::class.java)
    private val systemInformation: SystemInformation = mock(SystemInformation::class.java)

    private val categoryRepositoryImpl = CategoryRepositoryImpl(categoryRemoteDataSource, systemInformation)

    val categoryResponse = CategoryResponse(
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

        val response = ParsedResponse.Success(categoryResponse)

        `when`(categoryRemoteDataSource.getCategories()).thenReturn(response)

        // When
        val result = categoryRepositoryImpl.getCategories()

        // Then
        val expectedResult = Either.Right(categoryBusiness)

        Assert.assertEquals(expectedResult, result)
    }
}