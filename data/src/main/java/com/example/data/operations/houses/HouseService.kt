package com.example.data.operations.houses

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface HouseService {
    @GET("/type/2")
    fun getHouses(): Deferred<Response<ResponseBody>>
}