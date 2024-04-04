package com.superbeta.wallbyte_pro.home.data.local

import com.superbeta.wallbyte_pro.models.WallpaperDataModel
import com.superbeta.wallbyte_pro.utils.EmiDatabase

class WallpaperLocalDbService(private val db: EmiDatabase) : WallpaperLocalDao {

    override suspend fun saveWallpapersToDb(wallpapersList: List<WallpaperDataModel>) =
        db.wallpaperDao().saveWallpapersToDb(wallpapersList)


    override suspend fun getWallpapersFromDb(): List<WallpaperDataModel> =
        db.wallpaperDao().getWallpapersFromDb()


}