package com.example.data.operations.houses

import com.example.domain.operations.houses.HouseBusiness

data class HouseResponse(
    val id: String,
    val name: String,
    val region: String,
    val title: String
)

fun HouseResponse.toDomain() = HouseBusiness(id, name, region, title)