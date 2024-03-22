package com.superbeta.wallbyte_pro.home.data.local

import androidx.paging.PagingSource
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
}