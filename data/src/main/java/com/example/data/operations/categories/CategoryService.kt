package com.example.data.operations.categories

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface CategoryService {
    @GET("/categories")
    fun getCategories(): Deferred<Response<ResponseBody>>
}