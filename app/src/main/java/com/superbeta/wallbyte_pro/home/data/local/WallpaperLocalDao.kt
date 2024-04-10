package com.superbeta.wallbyte_pro.home.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.superbeta.wallbyte_pro.models.WallpaperDataModel

@Dao
interface WallpaperLocalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWallpapersToDb(wallpapersList: List<WallpaperDataModel>)

    @Query("SELECT * FROM wallpaper ORDER BY wallpaperId DESC")
    suspend fun getWallpapersFromDb(): List<WallpaperDataModel>

    @Query("SELECT * FROM wallpaper WHERE wallpaper_category = :category ORDER BY wallpaperId DESC")
    suspend fun getWallpapersByCategoryFromDb(category: String): List<WallpaperDataModel>
}