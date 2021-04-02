package com.example.gameofthrones.operations.books

import com.example.domain.operations.books.BookBusiness

data class BookViewEntity(
    val name: String,
    val isbn: String,
    val authors: String,
    val numberOfPages: Int,
    val publisher: String,
    val country: String,
    val mediaType: String,
    val released: String
)

fun BookBusiness.toPresentation(): BookViewEntity {
        var authorsValue = ""

        authors.forEachIndexed { index, author -> authorsValue = when(index == 0) {
            true -> String.format("%s", author)
            false -> String.format("%s, %s", authorsValue, author)
        } }

        return BookViewEntity(name, isbn, authorsValue, numberOfPages, publisher, country, mediaType, released)
    }