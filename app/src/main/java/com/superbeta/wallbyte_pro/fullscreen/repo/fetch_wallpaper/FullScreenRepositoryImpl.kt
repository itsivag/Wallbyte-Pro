package com.superbeta.wallbyte_pro.fullscreen.repo.fetch_wallpaper

import com.superbeta.wallbyte_pro.models.WallpaperDataModel

interface FullScreenRepository {

    suspend fun getFullScreenWallpaper(wallpaperId : Int): WallpaperDataModel
}