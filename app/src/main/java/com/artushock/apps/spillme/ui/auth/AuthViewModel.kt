package com.artushock.apps.spillme.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artushock.apps.spillme.repositories.AuthRepository
import com.artushock.apps.spillme.repositories.models.PreferencesRepository
import com.artushock.apps.spillme.repositories.models.auth.AuthStateResult
import com.artushock.apps.spillme.ui.model.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val preferencesRepository: PreferencesRepository,
) : ViewModel() {

    private val _authResultState: MutableStateFlow<ViewState<String>> =
        MutableStateFlow(ViewState.Success(""))
    val authResultState: StateFlow<ViewState<String>> get() = _authResultState

    init {
        val login = preferencesRepository.getLogin()
        val password = preferencesRepository.getPassword()
        if (login != null && password != null){
            signIn(login, password)
        }
    }

    fun signIn(login: String, password: String) {
        _authResultState.value = ViewState.Loading
        viewModelScope.launch {
            authRepository.signIn(login, password)
                .collect {
                    when (it) {
                        is AuthStateResult.SuccessStateResult -> {
                            _authResultState.value = ViewState.Success(it.authData.message)
                            preferencesRepository.putLogin(login)
                            preferencesRepository.putPassword(password)
                        }

                        is AuthStateResult.WrongStateResult -> {
                            _authResultState.value = ViewState.Error(
                                Throwable("")
                            )
                        }
                    }
                }
        }
    }

}