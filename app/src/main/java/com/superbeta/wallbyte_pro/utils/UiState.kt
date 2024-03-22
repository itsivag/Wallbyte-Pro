package com.superbeta.wallbyte_pro.utils

sealed class UiState {
    data object Loading : UiState()
    data object Success : UiState()
    data class Error(val errorMessage: String) : UiState()
}
