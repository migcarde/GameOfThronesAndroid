package com.example.data.operations.books

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
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.io.EOFException

class BooksRemoteDataSourceTest {

    private val bookService = mock(BookService::class.java)
    private val jsonParser = mock(JsonParser::class.java)
    private val systemInformation = mock(SystemInformation::class.java)

    private val responseParser = ResponseParser(jsonParser)
    private val bookRemoteDataSource = BookRemoteDataSource(bookService, responseParser)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Get books - Success`() = runBlocking {
        // Given
        val json = "test"
        val mockResponse = mock(BookResponse::class.java)
        val bookResponse = listOf(mockResponse)
        val responseBody = mock(ResponseBody::class.java)
        val response: Response<ResponseBody> = mock(Response::class.java) as Response<ResponseBody>

        Mockito.`when`(systemInformation.hasConnection).thenReturn(true)
        Mockito.`when`(responseBody.string()).thenReturn(json)
        Mockito.`when`(response.isSuccessful).thenReturn(true)
        Mockito.`when`(response.body()).thenReturn(responseBody)
        Mockito.`when`(bookService.getBooks()).thenReturn(CompletableDeferred(response))
        Mockito.`when`(jsonParser.fromJsonList(json, BookResponse::class.java)).thenReturn(bookResponse)

        val responseParsed = ParsedResponse.Success(bookResponse)

        // When
        val result = bookRemoteDataSource.getBooks()

        // Then
        Assert.assertEquals(responseParsed, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Get books - Server error`() = runBlocking {
        // Given
        val json = "test"
        val bookResponse = mock(BookResponse::class.java)
        val responseBody = mock(ResponseBody::class.java)
        val response: Response<ResponseBody> = mock(Response::class.java) as Response<ResponseBody>

        Mockito.`when`(systemInformation.hasConnection).thenReturn(true)
        Mockito.`when`(responseBody.string())
            .thenReturn(json).thenThrow(EOFException("End of input"))
        Mockito.`when`(response.isSuccessful).thenReturn(false)
        Mockito.`when`(response.errorBody()).thenReturn(responseBody)
        Mockito.`when`(bookService.getBooks()).thenReturn(CompletableDeferred(response))
        Mockito.`when`(jsonParser.fromJson(json, ErrorResponse::class.java)).thenReturn(
            ErrorResponse(listOf(
            State(status = "500", "Server error")
        ))
        )

        val responseParsed = ParsedResponse.Failure(RepositoryFailure.ServerError)

        // When
        val result = bookRemoteDataSource.getBooks()

        // Then
        Assert.assertEquals(responseParsed, result)
    }
}