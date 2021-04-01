package com.example.data.operations.categories

import com.example.data.ParsedResponse
import com.example.data.ResponseParser
import com.example.domain.operations.categories.CategoriesError
import okhttp3.ResponseBody
import retrofit2.Response

class CategoryRemoteDataSource(private val categoryService: CategoryService, private val responseParser: ResponseParser) {
    suspend fun getCategories(): ParsedResponse<CategoriesError, CategoryResponse> {
        val response: Response<ResponseBody> = categoryService.getCategories().await()

        return responseParser.parse(response)
    }
}