package com.example.gameofthrones

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.commons.data.types.Either
import com.example.commons.mockito.any
import com.example.commons.mockito.eq
import com.example.domain.RepositoryFailure
import com.example.domain.operations.books.BookBusiness
import com.example.domain.operations.books.BooksFailure
import com.example.domain.operations.books.GetBooks
import com.example.gameofthrones.operations.books.BookViewEntity
import com.example.gameofthrones.operations.books.BooksViewModel
import com.example.gameofthrones.operations.books.BooksViewState
import com.example.gameofthrones.operations.books.BooksViewTransition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class BooksViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // View model parameters
    private val state = Mockito.mock(Observer::class.java as Class<Observer<BooksViewState>>)
    private val transition =
        Mockito.mock(Observer::class.java as Class<Observer<BooksViewTransition>>)
    private val getBooks = Mockito.mock(GetBooks::class.java)

    private val viewModel = BooksViewModel(getBooks)

    // Example values for testing
    val business = listOf(
        BookBusiness(
            name = "Test",
            isbn = "Test isbn",
            authors = listOf("Test authors"),
            numberOfPages = 100,
            publisher = "Test publisher",
            country = "Test country",
            mediaType = "Test media",
            released = "Test released"
        )
    )

    val expectedResult = listOf(
        BookViewEntity(
            name = "Test",
            isbn = "Test isbn",
            authors = "Test authors",
            numberOfPages = 100,
            publisher = "Test publisher",
            country = "Test country",
            mediaType = "Test media",
            released = "Test released"
        )
    )

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(Dispatchers.Default)
    }

    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks()
    }

    @Test
    fun `Get books - Success`() = runBlocking {
        // Given
        viewModel.getState().observeForever(state)

        val success = Either.Right(business)

        val argumentCaptor = ArgumentCaptor.forClass(BooksViewState.Books::class.java)

        Mockito.`when`(getBooks.invoke(eq(Unit), any())).thenAnswer {
            it.getArgument<(Either<BooksFailure, List<BookBusiness>>) -> Unit>(1)
                .invoke(success)
        }
        Mockito.`when`(getBooks.run(Unit)).thenReturn(success)

        // When
        viewModel.getBooks()

        // Then
        Mockito.verify(state, Mockito.times(3)).onChanged(argumentCaptor.capture())

        val currentState = argumentCaptor.allValues[0]

        Assert.assertEquals(
            BooksViewState.Books(loading = false, data = expectedResult),
            currentState
        )
    }

    @Test
    fun `Get books - No internet`() = runBlocking {
        // Given
        viewModel.getTransition().observeForever(transition)

        val failure = Either.Left(BooksFailure.Repository(RepositoryFailure.NoInternet))

        val argumentCaptor = ArgumentCaptor.forClass(BooksViewTransition.OnNoInternet::class.java)

        Mockito.`when`(getBooks.invoke(eq(Unit), any())).thenAnswer {
            it.getArgument<(Either<BooksFailure, List<BookBusiness>>) -> Unit>(1)
                .invoke(failure)
        }
        Mockito.`when`(getBooks.run(Unit)).thenReturn(failure)

        // When
        viewModel.getBooks()

        // Then
        Mockito.verify(transition).onChanged(argumentCaptor.capture())

        val currentTransition = argumentCaptor.allValues[0]

        Assert.assertEquals(
            BooksViewTransition.OnNoInternet,
            currentTransition
        )
    }

    @Test
    fun `Get books - Unknown`() = runBlocking {
        // Given
        viewModel.getTransition().observeForever(transition)

        val failure = Either.Left(BooksFailure.Repository(RepositoryFailure.Unknown))

        val argumentCaptor = ArgumentCaptor.forClass(BooksViewTransition.OnUnknown::class.java)

        Mockito.`when`(getBooks.invoke(eq(Unit), any())).thenAnswer {
            it.getArgument<(Either<BooksFailure, List<BookBusiness>>) -> Unit>(1)
                .invoke(failure)
        }
        Mockito.`when`(getBooks.run(Unit)).thenReturn(failure)

        // When
        viewModel.getBooks()

        // Then
        Mockito.verify(transition).onChanged(argumentCaptor.capture())

        val currentTransition = argumentCaptor.allValues[0]

        Assert.assertEquals(
            BooksViewTransition.OnUnknown,
            currentTransition
        )
    }
}