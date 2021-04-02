package com.example.data.operations.houses

import com.example.commons.data.types.Either
import com.example.commons_android.system.SystemInformation
import com.example.data.ParsedResponse
import com.example.domain.RepositoryFailure
import com.example.domain.operations.houses.HouseBusiness
import com.example.domain.operations.houses.HouseFailure
import com.example.domain.operations.houses.HouseRepository

class HouseRepositoryImpl(
    private val houseRemoteDataSource: HouseRemoteDataSource,
    private val systemInformation: SystemInformation
) : HouseRepository {
    override suspend fun getHouses(): Either<HouseFailure, List<HouseBusiness>> {
        return when (systemInformation.hasConnection) {
            true -> try {
                when (val response = houseRemoteDataSource.getHouses()) {
                    is ParsedResponse.Success -> Either.Right(response.success.map { it.toDomain() })
                    is ParsedResponse.Failure -> Either.Left(HouseFailure.Repository(response.failure))
                    else -> Either.Left(HouseFailure.Repository(RepositoryFailure.Unknown))
                }
            } catch (e: Exception) {
                Either.Left(HouseFailure.Repository(RepositoryFailure.Unknown))
            }
            false -> Either.Left(HouseFailure.Repository(RepositoryFailure.NoInternet))
        }
    }
}