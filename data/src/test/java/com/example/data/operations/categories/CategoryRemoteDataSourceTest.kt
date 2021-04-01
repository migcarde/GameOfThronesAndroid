package com.example.data.operations.categories

import com.example.commons.json.JsonParser
import com.example.commons_android.system.SystemInformation
import com.example.data.ErrorResponse
import com.example.data.ParsedResponse
import com.example.data.ResponseParser
import com.example.data.State
import com.example.domain.RepositoryFailure
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.io.EOFException

class CategoryRemoteDataSourceTest {

    private val categoryService: CategoryService = mock(CategoryService::class.java)
    private val jsonParser: JsonParser = mock(JsonParser::class.java)
    private val responseParser = ResponseParser(jsonParser)
    private val systemInformation: SystemInformation = mock(SystemInformation::class.java)

    private val categoryRemoteDataSource = CategoryRemoteDataSource(categoryService, responseParser)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Get categories - Success`() = runBlocking {
        // Given
        val json = "test"
        val categoryResponse = mock(CategoryResponse::class.java)
        val responseBody = mock(ResponseBody::class.java)
        val response: Response<ResponseBody> = mock(Response::class.java) as Response<ResponseBody>

        `when`(systemInformation.hasConnection).thenReturn(true)
        `when`(responseBody.string()).thenReturn(json)
        `when`(response.isSuccessful).thenReturn(true)
        `when`(response.body()).thenReturn(responseBody)
        `when`(categoryService.getCategories()).thenReturn(CompletableDeferred(response))
        `when`(jsonParser.fromJson(json, CategoryResponse::class.java)).thenReturn(categoryResponse)

        val responseParsed = ParsedResponse.Success(categoryResponse)

        // When
        val result = categoryRemoteDataSource.getCategories()

        // Then
        Assert.assertEquals(responseParsed, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Get categories - Server error`() = runBlocking {
        // Given
        val json = "test"
        val categoryResponse = mock(CategoryResponse::class.java)
        val responseBody = mock(ResponseBody::class.java)
        val response: Response<ResponseBody> = mock(Response::class.java) as Response<ResponseBody>

        `when`(systemInformation.hasConnection).thenReturn(true)
        `when`(responseBody.string()).thenReturn(json).thenThrow(EOFException("End of input"))
        `when`(response.isSuccessful).thenReturn(false)
        `when`(response.errorBody()).thenReturn(responseBody)
        `when`(categoryService.getCategories()).thenReturn(CompletableDeferred(response))
        `when`(jsonParser.fromJson(json, ErrorResponse::class.java)).thenReturn(ErrorResponse(listOf(
            State(status = "500", "Server error")
        )))

        val responseParsed = ParsedResponse.Failure(RepositoryFailure.ServerError)

        // When
        val result = categoryRemoteDataSource.getCategories()

        // Then
        Assert.assertEquals(responseParsed, result)
    }
}