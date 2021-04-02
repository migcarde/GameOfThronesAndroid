package com.example.gameofthrones.operations.books

import com.example.domain.RepositoryFailure
import com.example.domain.operations.books.BookBusiness
import com.example.domain.operations.books.BooksFailure
import com.example.domain.operations.books.GetBooks
import com.example.gameofthrones.base.BaseViewModel

class BooksViewModel(private val getBooks: GetBooks) :
    BaseViewModel<BooksViewState, BooksViewTransition>() {

    val state by lazy {
        viewState.value as? BooksViewState.Books ?: BooksViewState.Books()
    }

    fun getBooks() {
        if (state.data.isNullOrEmpty()) {
            viewState.value = state.apply { loading = true }
            getBooks(Unit) {
                viewState.value = state.apply { loading = false }
                it.fold(::handleFailure, ::handleSuccess)
            }
        }
    }

    private fun handleFailure(failure: BooksFailure) {
        viewTransition.value = when (failure) {
            is BooksFailure.Repository -> handleRepositoryFailure(failure.error)
            else -> BooksViewTransition.OnUnknown
        }
    }

    private fun handleRepositoryFailure(failure: RepositoryFailure): BooksViewTransition =
        when (failure) {
            is RepositoryFailure.NoInternet -> BooksViewTransition.OnNoInternet
            else -> BooksViewTransition.OnUnknown
        }

    private fun handleSuccess(business: List<BookBusiness>) {
        viewState.value = state.apply {
            data = business.map { it.toPresentation() }
        }
    }
}