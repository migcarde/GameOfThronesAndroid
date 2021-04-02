package com.example.gameofthrones.operations.books

sealed class BooksViewTransition {
    // Success

    // Failure
    // NOTE: Not considered cases like unauthorized or repository exception
    object OnNoInternet: BooksViewTransition()

    object OnUnknown: BooksViewTransition()
}