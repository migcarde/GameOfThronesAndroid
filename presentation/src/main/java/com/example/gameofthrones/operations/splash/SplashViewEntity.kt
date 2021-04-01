package com.example.gameofthrones.operations.splash

import com.example.domain.operations.categories.CategoryBusiness

data class SplashViewEntity(
    val categoryName: String,
    val type: Int
)

fun CategoryBusiness.toPresentation() = SplashViewEntity(categoryName = categoryName, type = type)