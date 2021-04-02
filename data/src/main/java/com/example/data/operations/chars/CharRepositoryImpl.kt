package com.example.data.operations.chars

import com.example.commons.data.types.Either
import com.example.commons_android.system.SystemInformation
import com.example.data.ParsedResponse
import com.example.domain.RepositoryFailure
import com.example.domain.operations.chars.CharBusiness
import com.example.domain.operations.chars.CharFailure
import com.example.domain.operations.chars.CharRepository

class CharRepositoryImpl(
    private val charRemoteDataSource: CharRemoteDataSource,
    private val systemInformation: SystemInformation
) : CharRepository {
    override suspend fun getHouses(): Either<CharFailure, List<CharBusiness>> =
        when (systemInformation.hasConnection) {
            true -> try {
                when (val response = charRemoteDataSource.getChars()) {
                    is ParsedResponse.Success -> Either.Right(response.success.map { it.toDomain() })
                    is ParsedResponse.Failure -> Either.Left(CharFailure.Repository(response.failure))
                    else -> Either.Left(CharFailure.Repository(RepositoryFailure.Unknown))
                }
            } catch (e: Exception) {
                Either.Left(CharFailure.Repository(RepositoryFailure.Unknown))
            }
            false -> Either.Left(CharFailure.Repository(RepositoryFailure.NoInternet))
        }
}