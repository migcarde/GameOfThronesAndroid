package com.example.gameofthrones.operations.categories

import com.example.gameofthrones.base.BaseViewModel
import com.example.gameofthrones.operations.splash.SplashViewEntity

class CategoriesViewModel() : BaseViewModel<CategoriesViewState, CategoriesViewTransition>() {

    val state by lazy {
        viewState.value as? CategoriesViewState.Categories ?: CategoriesViewState.Categories()
    }

    fun setCategories(categoriesSplashViewEntity: List<SplashViewEntity>) {
        viewState.value = state.apply {
            data = categoriesSplashViewEntity.map { it.toPresentation() }
        }
    }

    fun goTo(type: Int) = when (type) {
        0 -> viewTransition.value = CategoriesViewTransition.GoToBooks(type)
        else -> null // TODO: Not implemented
    }
}