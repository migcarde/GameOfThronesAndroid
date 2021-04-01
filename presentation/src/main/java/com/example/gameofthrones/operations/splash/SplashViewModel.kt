package com.example.gameofthrones.operations.splash

import com.example.domain.RepositoryFailure
import com.example.domain.operations.categories.CategoriesFailure
import com.example.domain.operations.categories.CategoryBusiness
import com.example.domain.operations.categories.GetCategories
import com.example.gameofthrones.base.BaseViewModel

class SplashViewModel(private val getCategories: GetCategories) :
    BaseViewModel<SplashViewState, SplashViewTransiton>() {

    val state by lazy {
        viewState.value as? SplashViewState.Categories ?: SplashViewState.Categories()
    }

    fun getCategories() {
        if (state.data.isNullOrEmpty()) {
            getCategories(Unit) {
                it.fold(::handleFailure, ::handleSuccess)
            }
        }
    }

    fun goToCategories(categories: List<SplashViewEntity>) {
        viewTransition.value = SplashViewTransiton.GoToCategories(categories)
    }

    private fun handleFailure(failure: CategoriesFailure) {
        viewTransition.value = when (failure) {
            is CategoriesFailure.Repository -> handleRepositoryFailure(failure.error)
            else -> SplashViewTransiton.OnUnknown
        }
    }

    private fun handleRepositoryFailure(failure: RepositoryFailure): SplashViewTransiton =
        when (failure) {
            is RepositoryFailure.NoInternet -> SplashViewTransiton.OnNoInternet
            else -> SplashViewTransiton.OnUnknown
        }

    private fun handleSuccess(business: List<CategoryBusiness>) {
        viewState.value = state.apply {
            data = business.map { it.toPresentation() }
        }
    }
}