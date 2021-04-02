package com.example.data.operations.books

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface BookService {
    @GET("/list/1")
    fun getBooks(): Deferred<Response<ResponseBody>>
}