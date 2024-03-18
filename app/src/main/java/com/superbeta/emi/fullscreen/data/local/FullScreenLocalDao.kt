package com.superbeta.emi.fullscreen.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.superbeta.emi.models.WallpaperDataModel

@Dao
interface FullScreenLocalDao {
    @Query("SELECT * FROM wallpaper WHERE wallpaperId = (:wallId)")
    suspend fun getWallpaperFromDb(wallId: Int): WallpaperDataModel
}