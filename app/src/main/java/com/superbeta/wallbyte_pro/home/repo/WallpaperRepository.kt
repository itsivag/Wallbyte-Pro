package com.superbeta.wallbyte_pro.home.repo

import com.superbeta.wallbyte_pro.models.WallpaperDataModel

interface WallpaperRepository {
    suspend fun getWallpapers(page: Int): List<WallpaperDataModel>
    suspend fun getWallpapersByCategory(page: Int, category: String): List<WallpaperDataModel>
}