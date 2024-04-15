package com.artushock.apps.spillme.ui.auth.models

data class AuthScreenStateModel(
    val isProgress: Boolean = false,
    val signInError: String? = null,
    val email: String = "",
    val emailError: Boolean = false,
    val password: String = "",
    val passwordError: Boolean = false,
    val buttonEnabled: Boolean = false,
)
