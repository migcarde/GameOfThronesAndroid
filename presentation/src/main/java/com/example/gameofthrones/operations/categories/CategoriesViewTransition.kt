package com.example.gameofthrones.operations.categories

sealed class CategoriesViewTransition {

    // Success
    data class GoToBooks(val type: Int): CategoriesViewTransition()

    data class GoToHouses(val type: Int): CategoriesViewTransition()

    data class GoToChars(val type: Int): CategoriesViewTransition()
}