package com.example.data.di

import com.example.data.InterceptorConnection
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit

class DataKoinConfiguration(private val baseUrl: String) {

    companion object {
        private const val CONNECT_TIMEOUT = 60L
        private const val READ_TIMEOUT = 60L
    }

    fun getModule() = module {
        // Retrofit
        single { InterceptorConnection() }
        single(named("gameOfThrones")) { createOkHttpClient(get()) }
    }

    private fun createOkHttpClient(interceptorConnection: InterceptorConnection): OkHttpClient {
        val interceptorLog: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val cookieManager = CookieManager().apply {
            setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER)
        }

        return OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(interceptorConnection)
            .addInterceptor(interceptorLog)
            .cookieJar(JavaNetCookieJar(cookieManager))
            .build()
    }

    private fun createRetrofitWithUrl(okHttpClient: OkHttpClient, baseUrl: String): Retrofit =
        createRetrofitWithUrl(okHttpClient, baseUrl)

    private fun createRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    private inline fun <reified T> createRetrofitImplementation(retrofit: Retrofit): T =
        retrofit.create(T::class.java)
}