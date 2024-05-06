package com.artushock.apps.spillme.repositories.models.auth

sealed class AuthStateResult {
    data class SuccessStateResult(val authData: AuthData): AuthStateResult()
    data class WrongStateResult(val authData: AuthData): AuthStateResult()
}

data class AuthData (
    val message: String
)
