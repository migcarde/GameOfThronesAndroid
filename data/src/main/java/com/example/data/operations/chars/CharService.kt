package com.example.data.operations.chars

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface CharService {
    @GET("/list/3")
    fun getChars(): Deferred<Response<ResponseBody>>
}