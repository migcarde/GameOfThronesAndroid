package com.example.gameofthrones.operations.categories

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

sealed class CategoriesViewState : Parcelable {

    @Parcelize
    data class Categories(
        var data: @RawValue List<CategoryViewEntity> = emptyList()
    ) : CategoriesViewState()
}