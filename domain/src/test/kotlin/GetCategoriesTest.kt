import com.example.commons.data.types.Either
import com.example.domain.RepositoryFailure
import com.example.domain.operations.categories.CategoriesFailure
import com.example.domain.operations.categories.CategoryBusiness
import com.example.domain.operations.categories.CategoryRepository
import com.example.domain.operations.categories.GetCategories
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class GetCategoriesTest {

    private val categoryRepository = mock(CategoryRepository::class.java)
    private val getCategories = GetCategories(categoryRepository)

    @Test
    fun `Get categories - Success`() = runBlocking {
        // Given
        val category = mock(CategoryBusiness::class.java)
        val expectedResult = Either.Right(listOf(category))

        `when`(categoryRepository.getCategories()).thenReturn(expectedResult)

        // When
        val result = getCategories.run(Unit)

        // Then
        Assert.assertEquals(result, expectedResult)
    }

    @Test
    fun `Get categories - Server error`() = runBlocking {
        // Given
        val expectedResult = Either.Left(CategoriesFailure.Repository(RepositoryFailure.ServerError))

        `when`(categoryRepository.getCategories()).thenReturn(expectedResult)

        // When
        val result = getCategories.run(Unit)

        // Then
        Assert.assertEquals(result, expectedResult)
    }

    @Test
    fun `Get categories - No internet`() = runBlocking {
        // Given
        val expectedResult = Either.Left(CategoriesFailure.Repository(RepositoryFailure.NoInternet))

        `when`(categoryRepository.getCategories()).thenReturn(expectedResult)

        // When
        val result = getCategories.run(Unit)

        // Then
        Assert.assertEquals(result, expectedResult)
    }

    @Test
    fun `Get categories - Unauthorized`() = runBlocking {
        // Given
        val expectedResult = Either.Left(CategoriesFailure.Repository(RepositoryFailure.Unauthorized))

        `when`(categoryRepository.getCategories()).thenReturn(expectedResult)

        // When
        val result = getCategories.run(Unit)

        // Then
        Assert.assertEquals(result, expectedResult)
    }

    @Test
    fun `Get categories - Unknown`() = runBlocking {
        // Given
        val expectedResult = Either.Left(CategoriesFailure.Repository(RepositoryFailure.ServerError))

        `when`(categoryRepository.getCategories()).thenReturn(expectedResult)

        // When
        val result = getCategories.run(Unit)

        // Then
        Assert.assertEquals(result, expectedResult)
    }
}