package com.superbeta.emi.utils

sealed class UiState {
    data object Loading : UiState()
    data object Success : UiState()
    data class Error(val errorMessage: String) : UiState()
}
