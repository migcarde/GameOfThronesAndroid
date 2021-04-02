package com.example.domain.operations.chars

import com.example.commons.data.types.Either
import com.example.domain.base.BaseUseCase

class GetChars(private val charRepository: CharRepository) :
    BaseUseCase<CharFailure, List<CharBusiness>, Unit>() {
    override suspend fun run(paramas: Unit): Either<CharFailure, List<CharBusiness>> =
        charRepository.getHouses()
}