package com.superbeta.emi.home.repo

import com.superbeta.emi.models.WallpaperDataModel

interface WallpaperRepository {

    suspend fun getWallpapers(): List<WallpaperDataModel>
}