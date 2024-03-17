package com.superbeta.emi.home.repo

import com.superbeta.emi.home.data.WallpaperDataModel

interface WallpaperRepository {

    suspend fun getWallpapers(): List<WallpaperDataModel>
}