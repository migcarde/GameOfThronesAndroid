package com.example.gameofthrones.operations.houses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

sealed class HousesViewState : Parcelable {

    @Parcelize
    data class Houses(
        var loading: Boolean = true,
        var data: @RawValue List<HouseViewEntity>? = null
    ) : HousesViewState()
}