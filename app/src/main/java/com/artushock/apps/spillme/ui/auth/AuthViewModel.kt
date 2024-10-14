package com.artushock.apps.spillme.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artushock.apps.spillme.repositories.AuthRepository
import com.artushock.apps.spillme.repositories.models.auth.AuthStateResult
import com.artushock.apps.spillme.ui.auth.models.AuthScreenStateModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private var model = AuthScreenStateModel()

    private val _authResultState: MutableStateFlow<AuthScreenStateModel> =
        MutableStateFlow(model)
    val authResultState: StateFlow<AuthScreenStateModel> get() = _authResultState

    private val _signInChannel = Channel<Unit>()
    val signInChannel: ReceiveChannel<Unit> get() = _signInChannel

    fun init(){
        _authResultState.update { model }
    }

    fun signIn() {
        loading(true)
        if (emailValid() && passwordValid()){
            remoteSignIn()
        } else {
            updateState(true)
        }
    }

    private fun remoteSignIn() {
        viewModelScope.launch {
            when (val auth = authRepository.signIn(model.email, model.password)) {
                is AuthStateResult.SuccessStateResult -> {
                    _signInChannel.send(Unit)
                }

                is AuthStateResult.WrongStateResult -> {
                    _authResultState.value = model.copy(signInError = auth.authData.message)
                }
            }
            loading(false)
        }
    }

    private fun loading(isProgress: Boolean) {
        _authResultState.value = model
            .copy(isProgress = isProgress)
        if (!isProgress) passwordChanged("")
    }

    fun emailChanged(email: String) {
        model = model.copy(email = email)
        updateState()
    }

    fun passwordChanged(password: String) {
        model = model.copy(password = password)
        updateState()
    }

    private fun updateState(checkErrors: Boolean = false) {
        model = model.copy(
            emailError = if (checkErrors) emailValid() else model.emailError,
            passwordError = if (checkErrors) passwordValid() else model.passwordError,
            buttonEnabled = emailValid() && passwordValid(),
        )
        _authResultState.update { model }
    }

    private fun emailValid(): Boolean {
        return model.email.isNotEmpty() && model.email.contains("@") && model.email.contains(".")
    }

    private fun passwordValid(): Boolean {
        return model.password.isNotEmpty()
    }
}