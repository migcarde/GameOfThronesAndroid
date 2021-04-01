package com.example.gameofthrones.di

import com.example.gameofthrones.operations.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class PresentationKoinConfiguration {

    fun getModule() = module {
        viewModel { SplashViewModel(get()) }
    }
}