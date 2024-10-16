package com.artushock.apps.spillme.repositories

import com.artushock.apps.spillme.repositories.models.auth.AuthStateResult

interface AuthRepository {
    suspend fun signIn(login: String, password: String): AuthStateResult
}