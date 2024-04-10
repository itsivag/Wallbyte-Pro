package com.superbeta.wallbyte_pro.home.data.remote

import com.superbeta.wallbyte_pro.models.WallpaperDataModel

interface WallpaperRemoteDao {
    suspend fun getWallpapersFromRemote(page: Int): List<WallpaperDataModel>
    suspend fun getWallpaperId(): Int
    suspend fun getWallpaperByCategoryFromRemote(
        page: Int,
        category: String
    ): List<WallpaperDataModel>
}