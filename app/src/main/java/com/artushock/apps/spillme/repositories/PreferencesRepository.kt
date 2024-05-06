package com.artushock.apps.spillme.repositories

import android.content.Context

class PreferencesRepository(
    context: Context
) {

    private val sp = context.getSharedPreferences("SpillMeSP", Context.MODE_PRIVATE)

    fun putLogin(login: String) = sp.edit().putString(USER_LOGIN_KEY, login).apply()
    fun getLogin() = sp.getString(USER_LOGIN_KEY, null)
    fun clearLogin() = sp.edit().remove(USER_LOGIN_KEY).apply()

    fun putPassword(login: String) = sp.edit().putString(USER_PASSWORD_KEY, login).apply()
    fun getPassword() = sp.getString(USER_PASSWORD_KEY, null)
    fun clearPassword() = sp.edit().remove(USER_PASSWORD_KEY).apply()

    companion object {
        private const val USER_LOGIN_KEY = "USER_LOGIN_KEY"
        private const val USER_PASSWORD_KEY = "USER_NAME_KEY"
    }

}