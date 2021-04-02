package com.example.data.operations.houses

import com.example.data.ParsedResponse
import com.example.data.ResponseParser
import okhttp3.ResponseBody
import retrofit2.Response

class HouseRemoteDataSource(private val houseService: HouseService, private val responseParser: ResponseParser) {
    suspend fun getHouses() : ParsedResponse<Any, List<HouseResponse>> {
        val response: Response<ResponseBody> = houseService.getHouses().await()

        return responseParser.parse(response)
    }
}