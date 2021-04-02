package com.example.gameofthrones.operations.houses

import com.example.domain.RepositoryFailure
import com.example.domain.operations.houses.GetHouses
import com.example.domain.operations.houses.HouseBusiness
import com.example.domain.operations.houses.HouseFailure
import com.example.gameofthrones.base.BaseViewModel

class HousesViewModel(private val getHouses: GetHouses) :
    BaseViewModel<HousesViewState, HousesViewTransition>() {

    val state by lazy {
        viewState.value as? HousesViewState.Houses ?: HousesViewState.Houses()
    }

    fun getHouses() {
        if (state.data.isNullOrEmpty()) {
            viewState.value = state.apply { loading = true }
            getHouses(Unit) {
                viewState.value = state.apply { loading = false }
                it.fold(::handleFailure, ::handleSuccess)
            }
        }
    }

    private fun handleFailure(failure: HouseFailure) {
        viewTransition.value = when (failure) {
            is HouseFailure.Repository -> handleRepositoryFailure(failure.error)
            else -> HousesViewTransition.OnUnknown
        }
    }

    private fun handleRepositoryFailure(failure: RepositoryFailure): HousesViewTransition =
        when (failure) {
            is RepositoryFailure.NoInternet -> HousesViewTransition.OnNoInternet
            else -> HousesViewTransition.OnUnknown
        }

    private fun handleSuccess(business: List<HouseBusiness>) {
        viewState.value = state.apply {
            data = business.map { it.toPresentation() }
        }
    }

}