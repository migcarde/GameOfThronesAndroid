package com.example.gameofthrones.operations.categories

import com.example.gameofthrones.base.BaseViewModel
import com.example.gameofthrones.operations.splash.SplashViewEntity

class CategoryViewModel() : BaseViewModel<CategoriesViewState, CategoriesViewTransition>() {

    val state by lazy {
        viewState.value as? CategoriesViewState.Categories ?: CategoriesViewState.Categories()
    }

    fun setCategories(categoriesSplashViewEntity: List<SplashViewEntity>) {
        viewState.value = state.apply {
            data = categoriesSplashViewEntity.map { it.toPresentation() }
        }
    }
}