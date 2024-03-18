package com.superbeta.emi.home.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.superbeta.emi.models.WallpaperDataModel

@Dao
interface WallpaperLocalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWallpapersToDb(wallpapersList: List<WallpaperDataModel>)

    @Query("SELECT * FROM wallpaper")
    suspend fun getWallpapersFromDb(): List<WallpaperDataModel>
}