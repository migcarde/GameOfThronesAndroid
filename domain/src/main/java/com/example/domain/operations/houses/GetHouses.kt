package com.example.domain.operations.houses

import com.example.commons.data.types.Either
import com.example.domain.base.BaseUseCase

class GetHouses(private val houseRepository: HouseRepository) :
    BaseUseCase<HouseFailure, List<HouseBusiness>, Unit>() {
    override suspend fun run(paramas: Unit): Either<HouseFailure, List<HouseBusiness>> =
        houseRepository.getHouses()
}