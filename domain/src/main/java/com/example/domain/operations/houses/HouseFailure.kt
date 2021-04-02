package com.example.domain.operations.houses

import com.example.domain.RepositoryFailure

sealed class HouseFailure {
    data class Repository(val error: RepositoryFailure): HouseFailure()
    data class Know(val error: HouseError): HouseFailure()
}