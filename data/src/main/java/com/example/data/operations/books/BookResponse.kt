package com.example.data.operations.books

import com.example.domain.operations.books.BookBusiness

data class BookResponse(
    val name: String,
    val isbn: String,
    val authors: List<String>,
    val numberOfPages: Int,
    val publisher: String,
    val country: String,
    val mediaType: String,
    val released: String
)

fun BookResponse.toDomain() =
    BookBusiness(name, isbn, authors, numberOfPages, publisher, country, mediaType, released)