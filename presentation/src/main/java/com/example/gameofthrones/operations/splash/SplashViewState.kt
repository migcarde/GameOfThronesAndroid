package com.example.gameofthrones.operations.splash

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

sealed class SplashViewState : Parcelable {

    @Parcelize
    data class Categories(
        var data: @RawValue List<SplashViewEntity>? = null
    ) : SplashViewState()
}