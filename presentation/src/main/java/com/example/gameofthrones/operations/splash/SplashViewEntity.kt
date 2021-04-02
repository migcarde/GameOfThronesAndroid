package com.example.gameofthrones.operations.splash

import android.os.Parcelable
import com.example.domain.operations.categories.CategoryBusiness
import kotlinx.parcelize.Parcelize

@Parcelize
data class SplashViewEntity(
    val categoryName: String,
    val type: Int
) : Parcelable

fun CategoryBusiness.toPresentation() = SplashViewEntity(categoryName = categoryName, type = type)