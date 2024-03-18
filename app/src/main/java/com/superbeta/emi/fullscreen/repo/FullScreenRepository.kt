package com.superbeta.emi.fullscreen.repo

import com.superbeta.emi.fullscreen.data.local.FullScreenLocalDao
import com.superbeta.emi.models.WallpaperDataModel

class FullScreenRepositoryImpl(private val fullScreenLocalDao: FullScreenLocalDao) :
    FullScreenRepository {
    override suspend fun getFullScreenWallpaper(wallpaperId: Int): WallpaperDataModel =
        fullScreenLocalDao.getWallpaperFromDb(wallpaperId)
}