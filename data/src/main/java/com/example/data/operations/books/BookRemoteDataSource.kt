package com.example.data.operations.books

import com.example.data.ParsedResponse
import com.example.data.ResponseParser
import okhttp3.ResponseBody
import retrofit2.Response

class BookRemoteDataSource(private val bookService: BookService, private val responseParser: ResponseParser) {
    suspend fun getBooks(): ParsedResponse<Any, List<BookResponse>> {
        val response: Response<ResponseBody> = bookService.getBooks().await()

        return responseParser.parse(response)
    }
}