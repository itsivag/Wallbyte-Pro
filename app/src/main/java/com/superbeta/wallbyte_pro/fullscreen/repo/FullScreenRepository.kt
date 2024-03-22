package com.superbeta.wallbyte_pro.fullscreen.repo

import com.superbeta.wallbyte_pro.fullscreen.data.local.FullScreenLocalDao
import com.superbeta.wallbyte_pro.models.WallpaperDataModel

class FullScreenRepositoryImpl(private val fullScreenLocalDao: FullScreenLocalDao) :
    FullScreenRepository {
    override suspend fun getFullScreenWallpaper(wallpaperId: Int): WallpaperDataModel =
        fullScreenLocalDao.getWallpaperFromDb(wallpaperId)
}