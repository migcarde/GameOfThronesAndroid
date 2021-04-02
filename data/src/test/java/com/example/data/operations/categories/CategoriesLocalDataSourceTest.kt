package com.example.data.operations.categories

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class CategoriesLocalDataSourceTest {

    private val mockCategoryDao = mock(CategoryDao::class.java)

    private val categoryLocalDataSource = CategoryLocalDataSource(mockCategoryDao)

    private val expectedResults = listOf(
        CategoryEntity(
            categoryName = "Test",
            type = 1
        )
    )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Get all categories - Success`() {
        // Given
        `when`(mockCategoryDao.getAll()).thenReturn(expectedResults)

        // Then
        val result = categoryLocalDataSource.getCategories()

        // Given
        Assert.assertEquals(expectedResults, result)
    }
}