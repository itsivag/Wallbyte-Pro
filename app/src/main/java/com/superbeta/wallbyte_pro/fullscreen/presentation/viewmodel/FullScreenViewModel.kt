package com.superbeta.wallbyte_pro.fullscreen.presentation.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.superbeta.wallbyte_pro.fullscreen.data.local.FullScreenLocalService
import com.superbeta.wallbyte_pro.fullscreen.repo.fetch_wallpaper.FullScreenRepository
import com.superbeta.wallbyte_pro.fullscreen.repo.fetch_wallpaper.FullScreenRepositoryImpl
import com.superbeta.wallbyte_pro.fullscreen.repo.download.AndroidDownloader
import com.superbeta.wallbyte_pro.fullscreen.repo.set_wallpaper.WallpaperSetType
import com.superbeta.wallbyte_pro.fullscreen.repo.set_wallpaper.WallpaperSetter
import com.superbeta.wallbyte_pro.models.WallpaperDataModel
import com.superbeta.wallbyte_pro.utils.RoomInstance
import com.superbeta.wallbyte_pro.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FullScreenViewModel(
    private val fullScreenRepository: FullScreenRepository,
    private val wallpaperSetter: WallpaperSetter,
    private val downloader: AndroidDownloader,
) : ViewModel() {

    private val _wallpaperState = MutableStateFlow<WallpaperDataModel?>(null)
    val wallpaperState: StateFlow<WallpaperDataModel?> = _wallpaperState

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    suspend fun getWallpaper(wallpaperId: Int) {
        viewModelScope.launch {
            _wallpaperState.value = fullScreenRepository.getFullScreenWallpaper(wallpaperId)
            if (_wallpaperState.value != null) {
                _uiState.value = UiState.Success
            } else {
                _uiState.value = UiState.Error("Error Getting Wallpaper")

            }
        }
    }

    suspend fun setWallpaper() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            _wallpaperState.value?.let { wallpaper ->
                wallpaperSetter.setWallpaper(wallpaper, WallpaperSetType.SET_WALLPAPER) { success ->
                    if (success) {
                        _uiState.value = UiState.Success
                    } else {
                        _uiState.value = UiState.Error("Error setting wallpaper")
                    }
                }
            }
        }
    }

    suspend fun cropAndSetWallpaper() {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            _wallpaperState.value?.let { wallpaper ->
                wallpaperSetter.setWallpaper(
                    wallpaper,
                    WallpaperSetType.CROP_AND_SET_WALLPAPER
                ) { success ->
                    if (success) {
                        _uiState.value = UiState.Success
                    } else {
                        _uiState.value = UiState.Error("Error setting wallpaper")
                    }
                }
            }
        }
    }

    suspend fun downloadWallpaper() {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            _wallpaperState.value?.let { wallpaper ->
                wallpaper.wallpaperUrl?.let {
                    wallpaper.wallpaperName?.let { it1 ->
                        downloader.downloadFile(it, it1) { success ->
                            if (success) {
                                _uiState.value = UiState.Success
                            } else {
                                _uiState.value = UiState.Error("Error downloading wallpaper")
                            }
                        }
                    }
                }
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application =
                    extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                val db = RoomInstance.getDb(application.applicationContext)
                val fullScreenRepository = FullScreenRepositoryImpl(FullScreenLocalService(db))

                val wallpaperSetter =
                    WallpaperSetter(application.applicationContext)
                val downloader = AndroidDownloader(application.applicationContext)


                return FullScreenViewModel(
                    fullScreenRepository,
                    wallpaperSetter,
                    downloader
                ) as T
            }
        }
    }
}