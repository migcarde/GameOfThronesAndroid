import com.example.commons.data.types.Either
import com.example.domain.RepositoryFailure
import com.example.domain.operations.chars.CharBusiness
import com.example.domain.operations.chars.CharFailure
import com.example.domain.operations.chars.CharRepository
import com.example.domain.operations.chars.GetChars
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class GetCharsTest {

    private val charRepository = mock(CharRepository::class.java)

    private val getChars = GetChars(charRepository)

    @Test
    fun `Get chars - Success`() = runBlocking {
        // Given
        val char = mock(CharBusiness::class.java)
        val expectedResult = Either.Right(listOf(char))

        `when`(charRepository.getHouses()).thenReturn(expectedResult)

        // When
        val result = getChars.run(Unit)

        // Then
        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get chars - Server error`() = runBlocking {
        // Given
        val expectedResult = Either.Left(CharFailure.Repository(RepositoryFailure.ServerError))

        `when`(charRepository.getHouses()).thenReturn(expectedResult)

        // When
        val result = getChars.run(Unit)

        // Then
        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get chars - No internet`() = runBlocking {
        // Given
        val expectedResult = Either.Left(CharFailure.Repository(RepositoryFailure.NoInternet))

        `when`(charRepository.getHouses()).thenReturn(expectedResult)

        // When
        val result = getChars.run(Unit)

        // Then
        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get chars - Unauthorized`() = runBlocking {
        // Given
        val expectedResult = Either.Left(CharFailure.Repository(RepositoryFailure.Unauthorized))

        `when`(charRepository.getHouses()).thenReturn(expectedResult)

        // When
        val result = getChars.run(Unit)

        // Then
        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get chars - Unknown`() = runBlocking {
        // Given
        val expectedResult = Either.Left(CharFailure.Repository(RepositoryFailure.Unknown))

        `when`(charRepository.getHouses()).thenReturn(expectedResult)

        // When
        val result = getChars.run(Unit)

        // Then
        Assert.assertEquals(expectedResult, result)
    }
}