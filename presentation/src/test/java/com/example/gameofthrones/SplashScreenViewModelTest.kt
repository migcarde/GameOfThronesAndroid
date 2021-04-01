package com.example.gameofthrones

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.commons.data.types.Either
import com.example.commons.mockito.any
import com.example.commons.mockito.eq
import com.example.domain.RepositoryFailure
import com.example.domain.operations.categories.CategoriesFailure
import com.example.domain.operations.categories.CategoryBusiness
import com.example.domain.operations.categories.GetCategories
import com.example.gameofthrones.operations.splash.SplashViewEntity
import com.example.gameofthrones.operations.splash.SplashViewModel
import com.example.gameofthrones.operations.splash.SplashViewState
import com.example.gameofthrones.operations.splash.SplashViewTransiton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class SplashScreenViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // View model parameters
    private val state = mock(Observer::class.java as Class<Observer<SplashViewState>>)
    private val transition = mock(Observer::class.java as Class<Observer<SplashViewTransiton>>)
    private val getCategories = mock(GetCategories::class.java)

    private val viewModel = SplashViewModel(getCategories)

    // Example values for testing
    val business = listOf(
        CategoryBusiness(
            categoryName = "Test",
            type = 0
        )
    )
    val expectedResult = listOf(
        SplashViewEntity(
            categoryName = "Test",
            type = 0
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
    fun `Get categories - Success`() = runBlocking {
        // Given
        viewModel.getState().observeForever(state)

        val success = Either.Right(business)

        val argumentCaptor = ArgumentCaptor.forClass(SplashViewState.Categories::class.java)

        `when`(getCategories.invoke(eq(Unit), any())).thenAnswer {
            it.getArgument<(Either<CategoriesFailure, List<CategoryBusiness>>) -> Unit>(1)
                .invoke(success)
        }
        `when`(getCategories.run(Unit)).thenReturn(success)

        // When
        viewModel.getCategories()

        // Then
        Mockito.verify(state).onChanged(argumentCaptor.capture())

        val currentState = argumentCaptor.allValues[0]

        Assert.assertEquals(
            SplashViewState.Categories(data = expectedResult),
            currentState
        )
    }

    @Test
    fun `Get categories - No internet`() = runBlocking {
        // Given
        viewModel.getTransition().observeForever(transition)

        val failure = Either.Left(CategoriesFailure.Repository(RepositoryFailure.NoInternet))

        val argumentCaptor = ArgumentCaptor.forClass(SplashViewTransiton.OnNoInternet::class.java)

        `when`(getCategories.invoke(eq(Unit), any())).thenAnswer {
            it.getArgument<(Either<CategoriesFailure, List<CategoryBusiness>>) -> Unit>(1)
                .invoke(failure)
        }
        `when`(getCategories.run(Unit)).thenReturn(failure)

        // When
        viewModel.getCategories()

        // Then
        Mockito.verify(transition).onChanged(argumentCaptor.capture())

        val currentTransition = argumentCaptor.allValues[0]

        Assert.assertEquals(
            SplashViewTransiton.OnNoInternet,
            currentTransition
        )
    }

    @Test
    fun `Get categories - Unknown`() = runBlocking {
        // Given
        viewModel.getTransition().observeForever(transition)

        val failure = Either.Left(CategoriesFailure.Repository(RepositoryFailure.Unknown))

        val argumentCaptor = ArgumentCaptor.forClass(SplashViewTransiton.OnUnknown::class.java)

        `when`(getCategories.invoke(eq(Unit), any())).thenAnswer {
            it.getArgument<(Either<CategoriesFailure, List<CategoryBusiness>>) -> Unit>(1)
                .invoke(failure)
        }
        `when`(getCategories.run(Unit)).thenReturn(failure)

        // When
        viewModel.getCategories()

        // Then
        Mockito.verify(transition).onChanged(argumentCaptor.capture())

        val currentTransition = argumentCaptor.allValues[0]

        Assert.assertEquals(
            SplashViewTransiton.OnUnknown,
            currentTransition
        )
    }

    @Test
    fun `Get categories - Go to categories`() = runBlocking {
        // Given
        viewModel.getTransition().observeForever(transition)

        // When
        viewModel.goToCategories(expectedResult)

        verify(transition).onChanged(
            ArgumentMatchers.refEq(
                SplashViewTransiton.GoToCategories(
                    expectedResult
                )
            )
        )
    }
}