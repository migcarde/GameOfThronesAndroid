package com.example.domain.operations.houses

import com.example.commons.data.types.Either

interface HouseRepository {
    suspend fun getHouses(): Either<HouseFailure, List<HouseBusiness>>
}