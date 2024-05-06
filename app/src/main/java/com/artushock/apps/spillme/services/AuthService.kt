package com.artushock.apps.spillme.services

import com.artushock.apps.spillme.repositories.models.auth.AuthStateResult

interface AuthService {

    suspend fun signIn(login: String, password: String): AuthStateResult

//    suspend fun signUp(login: String, password: String): AuthStateResult

}