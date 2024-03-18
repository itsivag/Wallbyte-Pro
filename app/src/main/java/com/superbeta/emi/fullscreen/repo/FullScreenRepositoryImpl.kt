package com.superbeta.emi.fullscreen.repo

import com.superbeta.emi.models.WallpaperDataModel

interface FullScreenRepository {

    suspend fun getFullScreenWallpaper(wallpaperId : Int): WallpaperDataModel
}