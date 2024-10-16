package com.artushock.apps.spillme.services

import com.artushock.apps.spillme.repositories.models.auth.AuthData
import com.artushock.apps.spillme.repositories.models.auth.AuthStateResult
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthServiceImpl @Inject constructor() : AuthService {
    override suspend fun signIn(login: String, password: String): AuthStateResult {
        val result = try {
            Firebase.auth.signInWithEmailAndPassword(login, password).await()
        } catch (e: Throwable) {
            return AuthStateResult.WrongStateResult(AuthData(e.message ?: "Auth error"))
        }
        val user: FirebaseUser? = result.user
        return if (user != null) AuthStateResult.SuccessStateResult(AuthData("Success"))
        else AuthStateResult.WrongStateResult(AuthData("Error"))
    }
}