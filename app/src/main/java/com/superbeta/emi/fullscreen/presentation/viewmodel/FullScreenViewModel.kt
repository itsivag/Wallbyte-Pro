package com.superbeta.emi.fullscreen.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.superbeta.emi.fullscreen.data.local.FullScreenLocalDao
import com.superbeta.emi.fullscreen.data.local.FullScreenLocalService
import com.superbeta.emi.fullscreen.repo.FullScreenRepository
import com.superbeta.emi.fullscreen.repo.FullScreenRepositoryImpl
import com.superbeta.emi.home.data.local.WallpaperLocalDbService
import com.superbeta.emi.home.data.remote.WallpaperRemoteDbService
import com.superbeta.emi.home.presentation.viewmodel.WallpaperViewModel
import com.superbeta.emi.home.repo.WallpaperRepositoryImpl
import com.superbeta.emi.models.WallpaperDataModel
import com.superbeta.emi.utils.RoomInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FullScreenViewModel(
    private val fullScreenRepository: FullScreenRepository,
) : ViewModel() {

//    private val _wallpaperState = MutableStateFlow<WallpaperDataModel?>(null)
//    val wallpaperState: StateFlow<WallpaperDataModel?> = _wallpaperState

    suspend fun getWallpaper(wallpaperId: Int) =
        fullScreenRepository.getFullScreenWallpaper(wallpaperId)


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
                return FullScreenViewModel(
                    fullScreenRepository
                ) as T
            }
        }
    }
}