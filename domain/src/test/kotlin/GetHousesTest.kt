import com.example.commons.data.types.Either
import com.example.domain.RepositoryFailure
import com.example.domain.operations.houses.GetHouses
import com.example.domain.operations.houses.HouseBusiness
import com.example.domain.operations.houses.HouseFailure
import com.example.domain.operations.houses.HouseRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class GetHousesTest {
    private val houseRepository = mock(HouseRepository::class.java)
    private val getHouses = GetHouses(houseRepository)

    @Test
    fun `Get houses - Success`() = runBlocking {
        // Given
        val house = mock(HouseBusiness::class.java)
        val expectedResult = Either.Right(listOf(house))

        `when`(houseRepository.getHouses()).thenReturn(expectedResult)

        // When
        val result = getHouses.run(Unit)

        // Then
        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get houses - Server error`() = runBlocking {
        // Given
        val expectedResult = Either.Left(HouseFailure.Repository(RepositoryFailure.ServerError))

        `when`(houseRepository.getHouses()).thenReturn(expectedResult)

        // When
        val result = getHouses.run(Unit)

        // Then
        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get houses - No internet`() = runBlocking {
        // Given
        val expectedResult = Either.Left(HouseFailure.Repository(RepositoryFailure.NoInternet))

        `when`(houseRepository.getHouses()).thenReturn(expectedResult)

        // When
        val result = getHouses.run(Unit)

        // Then
        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get houses - Unauthorized`() = runBlocking {
        // Given
        val expectedResult = Either.Left(HouseFailure.Repository(RepositoryFailure.Unauthorized))

        `when`(houseRepository.getHouses()).thenReturn(expectedResult)

        // When
        val result = getHouses.run(Unit)

        // Then
        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get houses - Unknown`() = runBlocking {
        // Given
        val expectedResult = Either.Left(HouseFailure.Repository(RepositoryFailure.Unknown))

        `when`(houseRepository.getHouses()).thenReturn(expectedResult)

        // When
        val result = getHouses.run(Unit)

        // Then
        Assert.assertEquals(expectedResult, result)
    }
}