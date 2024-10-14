package com.artushock.apps.spillme.repositories

interface PrefsRepository {

    fun setLogin(login: String)
    fun getLogin(): String

    fun setPassword(password: String)
    fun getPassword(): String

    fun setLastSignInTimestamp(timestamp: Long)
    fun getLastSignInTimestamp(): Long
}