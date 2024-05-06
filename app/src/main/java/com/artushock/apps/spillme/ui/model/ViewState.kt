package com.artushock.apps.spillme.ui.model

sealed class ViewState<out T> {
    data object Loading : ViewState<Nothing>()
    data class Error(val error: Throwable) : ViewState<Nothing>()
    data class Success<T>(val result: T) : ViewState<T>()
}