package com.example.gameofthrones.operations.books

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

sealed class BooksViewState : Parcelable {

    @Parcelize
    data class Books(
        var loading: Boolean = true,
        var data: @RawValue List<BookViewEntity>? = null
    ) : BooksViewState()
}