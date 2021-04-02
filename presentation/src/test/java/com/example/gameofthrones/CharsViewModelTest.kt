package com.example.gameofthrones

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.commons.data.types.Either
import com.example.commons.mockito.any
import com.example.commons.mockito.eq
import com.example.domain.RepositoryFailure
import com.example.domain.operations.chars.CharBusiness
import com.example.domain.operations.chars.CharFailure
import com.example.domain.operations.chars.GetChars
import com.example.gameofthrones.operations.chars.CharViewEntity
import com.example.gameofthrones.operations.chars.CharsViewModel
import com.example.gameofthrones.operations.chars.CharsViewState
import com.example.gameofthrones.operations.chars.CharsViewTransition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CharsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // View model parameters
    private val state = Mockito.mock(Observer::class.java as Class<Observer<CharsViewState>>)
    private val transition =
        Mockito.mock(Observer::class.java as Class<Observer<CharsViewTransition>>)
    private val getChars = Mockito.mock(GetChars::class.java)

    private val viewModel = CharsViewModel(getChars)

    // Example values for testing
    val business = listOf(
        CharBusiness(
            id = "1",
            name = "test",
            gender = "test gender",
            titles = emptyList(),
            aliases = emptyList(),
            allegiances = emptyList(),
            playedBy = emptyList()
        )
    )

    val expectedResult = listOf(
        CharViewEntity(
            id = "1",
            name = "test",
            gender = "test gender"
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
    fun `Get chars - Success`() = runBlocking {
        // Given
        viewModel.getState().observeForever(state)

        val success = Either.Right(business)

        val argumentCaptor = ArgumentCaptor.forClass(CharsViewState.Chars::class.java)

        Mockito.`when`(getChars.invoke(eq(Unit), any())).thenAnswer {
            it.getArgument<(Either<CharFailure, List<CharBusiness>>) -> Unit>(1)
                .invoke(success)
        }
        Mockito.`when`(getChars.run(Unit)).thenReturn(success)

        // When
        viewModel.getChars()

        // Then
        Mockito.verify(state, Mockito.times(3)).onChanged(argumentCaptor.capture())

        val currentState = argumentCaptor.allValues[0]

        Assert.assertEquals(
            CharsViewState.Chars(loading = false, data = expectedResult),
            currentState
        )
    }

    @Test
    fun `Get houses - No internet`() = runBlocking {
        // Given
        viewModel.getTransition().observeForever(transition)

        val failure = Either.Left(CharFailure.Repository(RepositoryFailure.NoInternet))

        val argumentCaptor = ArgumentCaptor.forClass(CharsViewTransition.OnNoInternet::class.java)

        Mockito.`when`(getChars.invoke(eq(Unit), any())).thenAnswer {
            it.getArgument<(Either<CharFailure, List<CharBusiness>>) -> Unit>(1)
                .invoke(failure)
        }
        Mockito.`when`(getChars.run(Unit)).thenReturn(failure)

        // When
        viewModel.getChars()

        // Then
        Mockito.verify(transition).onChanged(argumentCaptor.capture())

        val currentState = argumentCaptor.allValues[0]

        Assert.assertEquals(
            CharsViewTransition.OnNoInternet,
            currentState
        )
    }

    @Test
    fun `Get houses - Unknown`() = runBlocking {
        // Given
        viewModel.getTransition().observeForever(transition)

        val failure = Either.Left(CharFailure.Repository(RepositoryFailure.Unknown))

        val argumentCaptor = ArgumentCaptor.forClass(CharsViewTransition.OnUnknown::class.java)

        Mockito.`when`(getChars.invoke(eq(Unit), any())).thenAnswer {
            it.getArgument<(Either<CharFailure, List<CharBusiness>>) -> Unit>(1)
                .invoke(failure)
        }
        Mockito.`when`(getChars.run(Unit)).thenReturn(failure)

        // When
        viewModel.getChars()

        // Then
        Mockito.verify(transition).onChanged(argumentCaptor.capture())

        val currentState = argumentCaptor.allValues[0]

        Assert.assertEquals(
            CharsViewTransition.OnUnknown,
            currentState
        )
    }
}