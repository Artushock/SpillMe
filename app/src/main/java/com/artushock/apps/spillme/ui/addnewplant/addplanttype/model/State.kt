package com.artushock.apps.spillme.ui.addnewplant.addplanttype.model

sealed class UiState<out T> {
    data object Loading: UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val throwable: Throwable) : UiState<Nothing>()
}
