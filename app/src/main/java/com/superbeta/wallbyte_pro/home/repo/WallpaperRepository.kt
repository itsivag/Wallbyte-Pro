package com.superbeta.wallbyte_pro.home.repo

import com.superbeta.wallbyte_pro.models.WallpaperDataModel
import kotlinx.coroutines.flow.Flow

interface WallpaperRepository {
    suspend fun getWallpapers(page : Int): List<WallpaperDataModel>
}