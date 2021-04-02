import com.example.commons.data.types.Either
import com.example.domain.RepositoryFailure
import com.example.domain.operations.books.BookBusiness
import com.example.domain.operations.books.BookRepository
import com.example.domain.operations.books.BooksFailure
import com.example.domain.operations.books.GetBooks
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class GetBooksTest {

    private val bookRepository = mock(BookRepository::class.java)

    private val getBooks = GetBooks(bookRepository)

    @Test
    fun `Get books - Success`() = runBlocking {
        // Given
        val book = mock(BookBusiness::class.java)
        val expectedResult = Either.Right(listOf(book))

        `when`(bookRepository.getBooks()).thenReturn(expectedResult)

        // When
        val result = getBooks.run(Unit)

        // Then
        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get books - Server error`() = runBlocking {
        // Given
        val expectedResult = Either.Left(BooksFailure.Repository(RepositoryFailure.ServerError))

        `when`(bookRepository.getBooks()).thenReturn(expectedResult)

        // When
        val result = getBooks.run(Unit)

        // Then
        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get books - No internet`() = runBlocking {
        // Given
        val expectedResult = Either.Left(BooksFailure.Repository(RepositoryFailure.NoInternet))

        `when`(bookRepository.getBooks()).thenReturn(expectedResult)

        // When
        val result = getBooks.run(Unit)

        // Then
        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get books - Unauthorized`() = runBlocking {
        // Given
        val expectedResult = Either.Left(BooksFailure.Repository(RepositoryFailure.Unauthorized))

        `when`(bookRepository.getBooks()).thenReturn(expectedResult)

        // When
        val result = getBooks.run(Unit)

        // Then
        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Get books - Unknown`() = runBlocking {
        // Given
        val expectedResult = Either.Left(BooksFailure.Repository(RepositoryFailure.Unknown))

        `when`(bookRepository.getBooks()).thenReturn(expectedResult)

        // When
        val result = getBooks.run(Unit)

        // Then
        Assert.assertEquals(expectedResult, result)
    }
}