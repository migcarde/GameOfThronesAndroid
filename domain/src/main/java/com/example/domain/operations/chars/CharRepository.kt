package com.example.domain.operations.chars

import com.example.commons.data.types.Either

interface CharRepository {
    suspend fun getHouses(): Either<CharFailure, List<CharBusiness>>
}