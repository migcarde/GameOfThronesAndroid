package com.example.gameofthrones.di

import com.example.gameofthrones.operations.books.BooksViewModel
import com.example.gameofthrones.operations.categories.CategoriesViewModel
import com.example.gameofthrones.operations.chars.CharsViewModel
import com.example.gameofthrones.operations.houses.HousesViewModel
import com.example.gameofthrones.operations.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class PresentationKoinConfiguration {

    fun getModule() = module {
        viewModel { SplashViewModel(get()) }
        viewModel { CategoriesViewModel() }
        viewModel { BooksViewModel(get()) }
        viewModel { HousesViewModel(get()) }
        viewModel { CharsViewModel(get()) }
    }
}