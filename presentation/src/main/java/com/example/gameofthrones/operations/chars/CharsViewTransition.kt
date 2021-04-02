package com.example.gameofthrones.operations.chars


sealed class CharsViewTransition {
    // Failure
    // NOTE: Not considered cases like unauthorized or repository exception
    object OnNoInternet : CharsViewTransition()

    object OnUnknown : CharsViewTransition()
}