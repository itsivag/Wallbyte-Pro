package com.superbeta.emi.home.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.superbeta.emi.home.data.WallpaperDataModel
import com.superbeta.emi.home.data.local.WallpaperDatabase
import com.superbeta.emi.home.data.local.WallpaperLocalDbService
import com.superbeta.emi.home.data.remote.WallpaperRemoteDbService
import com.superbeta.emi.home.repo.WallpaperRepository
import com.superbeta.emi.home.repo.WallpaperRepositoryImpl
import com.superbeta.emi.utils.RoomInstance
import com.superbeta.emi.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WallpaperViewModel(
    private val wallpaperRepository: WallpaperRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _wallpaperState = MutableStateFlow<List<WallpaperDataModel>>(listOf())
    val wallpaperState: StateFlow<List<WallpaperDataModel>> = _wallpaperState

    init {
        viewModelScope.launch {
            getWallpapers()
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = extras[APPLICATION_KEY] as Application
                val db = RoomInstance.getDb(application.applicationContext)

                val wallpaperRepository =
                    WallpaperRepositoryImpl(WallpaperLocalDbService(db), WallpaperRemoteDbService())
                return WallpaperViewModel(
                    wallpaperRepository
                ) as T
            }
        }
    }

    private suspend fun getWallpapers() {
        _wallpaperState.value = wallpaperRepository.getWallpapers()
        if (_wallpaperState.value.isNotEmpty()) {
            _uiState.value = UiState.Success
        } else if (_wallpaperState.value.isEmpty()) {
            _uiState.value = UiState.Error("error")
        }
    }

}