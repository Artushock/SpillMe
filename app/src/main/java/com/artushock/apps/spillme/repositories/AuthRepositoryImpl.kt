package com.artushock.apps.spillme.repositories

import com.artushock.apps.spillme.repositories.models.auth.AuthStateResult
import com.artushock.apps.spillme.services.AuthService
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
) : AuthRepository {

    val auth: FirebaseAuth by lazy { Firebase.auth }

    override suspend fun signIn(login: String, password: String): AuthStateResult {
        return withContext(Dispatchers.IO){
            authService.signIn(login, password)
        }
    }
}