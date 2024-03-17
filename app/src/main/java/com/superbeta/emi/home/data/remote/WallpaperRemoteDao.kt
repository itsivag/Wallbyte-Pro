package com.superbeta.emi.home.data.remote

import com.superbeta.emi.home.data.WallpaperDataModel

interface WallpaperRemoteDao {
    suspend fun getWallpapersFromRemote(): List<WallpaperDataModel>
}