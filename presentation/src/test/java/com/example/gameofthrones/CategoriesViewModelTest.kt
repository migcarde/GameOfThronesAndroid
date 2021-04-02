package com.example.gameofthrones

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.gameofthrones.operations.categories.CategoriesViewModel
import com.example.gameofthrones.operations.categories.CategoriesViewState
import com.example.gameofthrones.operations.categories.CategoriesViewTransition
import com.example.gameofthrones.operations.categories.CategoryViewEntity
import com.example.gameofthrones.operations.splash.SplashViewEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CategoriesViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // View model parameters
    private val state = Mockito.mock(Observer::class.java as Class<Observer<CategoriesViewState>>)
    private val transition =
        Mockito.mock(Observer::class.java as Class<Observer<CategoriesViewTransition>>)

    private val viewModel = CategoriesViewModel()

    // Example values for testing
    val splashViewEntity = listOf(
        SplashViewEntity(
            categoryName = "Test",
            type = 0
        )
    )

    val expectedResult = listOf(
        CategoryViewEntity(
            categoryName = "Test",
            type = 0
        )
    )

    val TYPE_BOOKS = 0
    val TYPE_HOUSES = 1
    val TYPE_CHARS = 2

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

        val argumentCaptor = ArgumentCaptor.forClass(CategoriesViewState::class.java)

        // When
        viewModel.setCategories(splashViewEntity)

        // Then
        Mockito.verify(state).onChanged(argumentCaptor.capture())

        val currentState = argumentCaptor.allValues[0]

        Assert.assertEquals(
            CategoriesViewState.Categories(data = expectedResult),
            currentState
        )
    }

    @Test
    fun `Get categories - Go to books`() = runBlocking {
        // Given
        viewModel.getTransition().observeForever(transition)

        val argumentCaptor =
            ArgumentCaptor.forClass(CategoriesViewTransition.GoToBooks(TYPE_BOOKS)::class.java)

        // When
        viewModel.goTo(TYPE_BOOKS)

        // Then
        Mockito.verify(transition).onChanged(argumentCaptor.capture())

        val currentState = argumentCaptor.allValues[0]

        Assert.assertEquals(
            CategoriesViewTransition.GoToBooks(TYPE_BOOKS),
            currentState
        )
    }

    @Test
    fun `Get categories - Go to houses`() = runBlocking {
        // Given
        viewModel.getTransition().observeForever(transition)

        val argumentCaptor =
            ArgumentCaptor.forClass(CategoriesViewTransition.GoToHouses(TYPE_HOUSES)::class.java)

        // When
        viewModel.goTo(TYPE_HOUSES)

        // Then
        Mockito.verify(transition).onChanged(argumentCaptor.capture())

        val currentState = argumentCaptor.allValues[0]

        Assert.assertEquals(
            CategoriesViewTransition.GoToHouses(TYPE_HOUSES),
            currentState
        )
    }

    @Test
    fun `Get categories - Go to characters`() = runBlocking {
        // Given
        viewModel.getTransition().observeForever(transition)

        val argumentCaptor =
            ArgumentCaptor.forClass(CategoriesViewTransition.GoToChars(TYPE_CHARS)::class.java)

        // When
        viewModel.goTo(TYPE_CHARS)

        // Then
        Mockito.verify(transition).onChanged(argumentCaptor.capture())

        val currentState = argumentCaptor.allValues[0]

        Assert.assertEquals(
            CategoriesViewTransition.GoToChars(TYPE_CHARS),
            currentState
        )
    }
}