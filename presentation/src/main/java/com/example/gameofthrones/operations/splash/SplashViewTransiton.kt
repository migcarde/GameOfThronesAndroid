package com.example.gameofthrones.operations.splash

sealed class SplashViewTransiton {

    // Success

    data class GoToCategories(val categories: List<SplashViewEntity>): SplashViewTransiton()

    // Errors
    // NOTE: Not considered cases like unauthorized or repository exception
    object OnNoInternet: SplashViewTransiton()

    object OnUnknown: SplashViewTransiton()

}