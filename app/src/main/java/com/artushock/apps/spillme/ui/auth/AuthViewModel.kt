package com.artushock.apps.spillme.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artushock.apps.spillme.repositories.AuthRepository
import com.artushock.apps.spillme.repositories.models.auth.AuthStateResult
import com.artushock.apps.spillme.ui.model.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authResultState: MutableStateFlow<ViewState<String>> =
        MutableStateFlow(ViewState.Success(""))
    val authResultState: StateFlow<ViewState<String>> get() = _authResultState

    fun signIn(email: String, password: String) {
        _authResultState.value = ViewState.Loading
        viewModelScope.launch {
            authRepository.signIn(email, password)
                .collect {
                    when (it) {
                        is AuthStateResult.SuccessStateResult -> {
                            _authResultState.value = ViewState.Success(it.authData.message)
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