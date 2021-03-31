package com.example.domain

sealed class RepositoryFailure {

    class RepositoryException(val message: String?) : RepositoryFailure()

    object NoInternet: RepositoryFailure()

    data class Unauthorized(val error: String?) : RepositoryFailure()

    object ServerError : RepositoryFailure()

    object Unknown : RepositoryFailure()
}