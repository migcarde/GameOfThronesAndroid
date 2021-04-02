package com.example.gameofthrones.operations.houses

sealed class HousesViewTransition {
    // Success

    // Failure
    // NOTE: Not considered cases like unauthorized or repository exception
    object OnNoInternet: HousesViewTransition()

    object OnUnknown: HousesViewTransition()
}