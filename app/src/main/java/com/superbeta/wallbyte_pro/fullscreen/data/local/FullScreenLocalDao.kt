package com.superbeta.wallbyte_pro.fullscreen.data.local

import androidx.room.Dao
import androidx.room.Query
import com.superbeta.wallbyte_pro.models.WallpaperDataModel

@Dao
interface FullScreenLocalDao {
    @Query("SELECT * FROM wallpaper WHERE wallpaperId = (:wallId)")
    suspend fun getWallpaperFromDb(wallId: Int): WallpaperDataModel
}