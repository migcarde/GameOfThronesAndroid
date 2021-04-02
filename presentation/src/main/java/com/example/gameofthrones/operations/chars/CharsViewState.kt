package com.example.gameofthrones.operations.chars

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

sealed class CharsViewState : Parcelable {

    @Parcelize
    data class Chars(
        var loading: Boolean = true,
        var data: @RawValue List<CharViewEntity>? = null
    ) : CharsViewState()
}