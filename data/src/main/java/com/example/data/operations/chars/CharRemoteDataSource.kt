package com.example.data.operations.chars

import com.example.data.ParsedResponse
import com.example.data.ResponseParser
import okhttp3.ResponseBody
import retrofit2.Response

class CharRemoteDataSource(
    private val charService: CharService,
    private val responseParser: ResponseParser
) {
    suspend fun getChars(): ParsedResponse<Any, List<CharResponse>> {
        val response: Response<ResponseBody> = charService.getChars().await()

        return responseParser.parse(response)
    }
}