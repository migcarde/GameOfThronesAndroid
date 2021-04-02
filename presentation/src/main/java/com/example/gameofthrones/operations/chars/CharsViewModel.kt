package com.example.gameofthrones.operations.chars

import com.example.domain.RepositoryFailure
import com.example.domain.operations.chars.CharBusiness
import com.example.domain.operations.chars.CharFailure
import com.example.domain.operations.chars.GetChars
import com.example.gameofthrones.base.BaseViewModel

class CharsViewModel(private val getChars: GetChars) :
    BaseViewModel<CharsViewState, CharsViewTransition>() {

    val state by lazy {
        viewState.value as? CharsViewState.Chars ?: CharsViewState.Chars()
    }

    fun getChars() {
        if (state.data.isNullOrEmpty()) {
            viewState.value = state.apply { loading = true }
            getChars(Unit) {
                viewState.value = state.apply { loading = false }
                it.fold(::handleFailure, ::handleSuccess)
            }
        }
    }

    private fun handleFailure(failure: CharFailure) {
        viewTransition.value = when (failure) {
            is CharFailure.Repository -> handleRepositoryFailure(failure.error)
            else -> CharsViewTransition.OnUnknown
        }
    }

    private fun handleRepositoryFailure(failure: RepositoryFailure): CharsViewTransition =
        when (failure) {
            is RepositoryFailure.NoInternet -> CharsViewTransition.OnNoInternet
            else -> CharsViewTransition.OnUnknown
        }

    private fun handleSuccess(business: List<CharBusiness>) {
        viewState.value = state.apply {
            data = business.map { it.toPresentation() }
        }
    }
}