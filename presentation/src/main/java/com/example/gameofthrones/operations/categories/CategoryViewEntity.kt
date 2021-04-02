package com.example.gameofthrones.operations.categories

import com.example.gameofthrones.operations.splash.SplashViewEntity

data class CategoryViewEntity(
    val categoryName: String,
    val type: Int
)

fun SplashViewEntity.toPresentation() = CategoryViewEntity(categoryName, type)