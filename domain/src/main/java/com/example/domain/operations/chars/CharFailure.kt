package com.example.domain.operations.chars

import com.example.domain.RepositoryFailure

sealed class CharFailure {
    data class Know(val error: CharError) : CharFailure()
    data class Repository(val error: RepositoryFailure) : CharFailure()
}