package com.superbeta.emi.home.data.remote

import com.superbeta.emi.models.WallpaperDataModel

interface WallpaperRemoteDao {
    suspend fun getWallpapersFromRemote(): List<WallpaperDataModel>
}