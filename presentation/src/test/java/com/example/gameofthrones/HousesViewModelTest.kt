package com.example.gameofthrones

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.commons.data.types.Either
import com.example.commons.mockito.any
import com.example.commons.mockito.eq
import com.example.domain.RepositoryFailure
import com.example.domain.operations.houses.GetHouses
import com.example.domain.operations.houses.HouseBusiness
import com.example.domain.operations.houses.HouseFailure
import com.example.gameofthrones.operations.houses.HouseViewEntity
import com.example.gameofthrones.operations.houses.HousesViewModel
import com.example.gameofthrones.operations.houses.HousesViewState
import com.example.gameofthrones.operations.houses.HousesViewTransition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class HousesViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // View model parameters
    private val state = Mockito.mock(Observer::class.java as Class<Observer<HousesViewState>>)
    private val transition =
        Mockito.mock(Observer::class.java as Class<Observer<HousesViewTransition>>)
    private val getHouses = Mockito.mock(GetHouses::class.java)

    private val viewModel = HousesViewModel(getHouses)

    // Example values for testing
    val business = listOf(
        HouseBusiness(
            id = "1",
            name = "Test",
            region = "the north",
            title = "Test title"
        )
    )

    val expectedResult = listOf(
        HouseViewEntity(
            id = "1",
            name = "Test",
            region = "the north",
            title = "Test title",
            picture = "https://bit.ly/2Gcq0r4"
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
    fun `Get houses - Success`() = runBlocking {
        // Given
        viewModel.getState().observeForever(state)

        val success = Either.Right(business)

        val argumentCaptor = ArgumentCaptor.forClass(HousesViewState.Houses::class.java)

        Mockito.`when`(getHouses.invoke(eq(Unit), any())).thenAnswer {
            it.getArgument<(Either<HouseFailure, List<HouseBusiness>>) -> Unit>(1)
                .invoke(success)
        }
        Mockito.`when`(getHouses.run(Unit)).thenReturn(success)

        // When
        viewModel.getHouses()

        // Then
        Mockito.verify(state, Mockito.times(3)).onChanged(argumentCaptor.capture())

        val currentState = argumentCaptor.allValues[0]

        Assert.assertEquals(
            HousesViewState.Houses(loading = false, data = expectedResult),
            currentState
        )
    }

    @Test
    fun `Get houses - No internet`() = runBlocking {
        // Given
        viewModel.getTransition().observeForever(transition)

        val failure = Either.Left(HouseFailure.Repository(RepositoryFailure.NoInternet))

        val argumentCaptor = ArgumentCaptor.forClass(HousesViewTransition.OnNoInternet::class.java)

        Mockito.`when`(getHouses.invoke(eq(Unit), any())).thenAnswer {
            it.getArgument<(Either<HouseFailure, List<HouseBusiness>>) -> Unit>(1)
                .invoke(failure)
        }
        Mockito.`when`(getHouses.run(Unit)).thenReturn(failure)

        // When
        viewModel.getHouses()

        // Then
        Mockito.verify(transition).onChanged(argumentCaptor.capture())

        val currentState = argumentCaptor.allValues[0]

        Assert.assertEquals(
            HousesViewTransition.OnNoInternet,
            currentState
        )
    }

    @Test
    fun `Get houses - Unknown`() = runBlocking {
        // Given
        viewModel.getTransition().observeForever(transition)

        val failure = Either.Left(HouseFailure.Repository(RepositoryFailure.Unknown))

        val argumentCaptor = ArgumentCaptor.forClass(HousesViewTransition.OnUnknown::class.java)

        Mockito.`when`(getHouses.invoke(eq(Unit), any())).thenAnswer {
            it.getArgument<(Either<HouseFailure, List<HouseBusiness>>) -> Unit>(1)
                .invoke(failure)
        }
        Mockito.`when`(getHouses.run(Unit)).thenReturn(failure)

        // When
        viewModel.getHouses()

        // Then
        Mockito.verify(transition).onChanged(argumentCaptor.capture())

        val currentState = argumentCaptor.allValues[0]

        Assert.assertEquals(
            HousesViewTransition.OnUnknown,
            currentState
        )
    }
}