package com.artushock.apps.spillme.repositories

import com.artushock.apps.spillme.repositories.models.auth.AuthStateResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signIn(login: String, password: String): Flow<AuthStateResult>
}