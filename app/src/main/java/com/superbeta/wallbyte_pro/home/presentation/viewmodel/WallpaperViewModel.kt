package com.superbeta.wallbyte_pro.home.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.superbeta.wallbyte_pro.home.data.local.WallpaperLocalDbService
import com.superbeta.wallbyte_pro.home.data.remote.WallpaperRemoteDbService
import com.superbeta.wallbyte_pro.home.repo.WallpaperRepository
import com.superbeta.wallbyte_pro.home.repo.WallpaperRepositoryImpl
import com.superbeta.wallbyte_pro.models.WallpaperDataModel
import com.superbeta.wallbyte_pro.utils.RoomInstance
import com.superbeta.wallbyte_pro.utils.UiState
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

    private var _currPage by mutableIntStateOf(0)

    init {
        viewModelScope.launch {
            getWallpapers()
        }
    }

    suspend fun refreshWallpapersList() {
        _wallpaperState.value = emptyList()
        _currPage = 0
        getWallpapers()
    }

    suspend fun getWallpapers() {
        Log.e("page", _currPage.toString())
        _wallpaperState.value = wallpaperRepository.getWallpapers(_currPage)
        if (_wallpaperState.value.isNotEmpty()) {
            _uiState.value = UiState.Success
            _currPage++
        } else if (_wallpaperState.value.isEmpty()) {
            _uiState.value = UiState.Error("error")
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
}