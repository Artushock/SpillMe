package com.artushock.apps.spillme.repositories

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PrefsRepositoryImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
) : PrefsRepository {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    override fun setLogin(login: String) {
        sharedPreferences.edit().putString(PREFS_KEY_LOGIN, login).apply()
    }

    override fun getLogin(): String {
        return sharedPreferences.getString(PREFS_KEY_LOGIN, "") ?: ""
    }

    override fun setPassword(password: String) {
        sharedPreferences.edit().putString(PREFS_KEY_PASSWORD, password).apply()
    }

    override fun getPassword(): String {
        return sharedPreferences.getString(PREFS_KEY_PASSWORD, "") ?: ""
    }

    override fun setLastSignInTimestamp(timestamp: Long) {
        sharedPreferences.edit().putLong(PREFS_KEY_LOGIN_TIMESTAMP, timestamp).apply()
    }

    override fun getLastSignInTimestamp(): Long {
        return sharedPreferences.getLong(PREFS_KEY_LOGIN_TIMESTAMP, 0L)
    }

    companion object {
        private const val PREFS_KEY_LOGIN = "PREFS_KEY_LOGIN"
        private const val PREFS_KEY_PASSWORD = "PREFS_KEY_PASSWORD"
        private const val PREFS_KEY_LOGIN_TIMESTAMP = "PREFS_KEY_LOGIN_TIMESTAMP"
    }

}