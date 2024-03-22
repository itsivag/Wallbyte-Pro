package com.superbeta.wallbyte_pro.fullscreen.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.superbeta.wallbyte_pro.fullscreen.data.local.FullScreenLocalService
import com.superbeta.wallbyte_pro.fullscreen.repo.FullScreenRepository
import com.superbeta.wallbyte_pro.fullscreen.repo.FullScreenRepositoryImpl
import com.superbeta.wallbyte_pro.utils.RoomInstance

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