package com.superbeta.wallbyte_pro.fullscreen.repo

import com.superbeta.wallbyte_pro.models.WallpaperDataModel

interface FullScreenRepository {

    suspend fun getFullScreenWallpaper(wallpaperId : Int): WallpaperDataModel
}