package com.superbeta.emi.home.data.local

import com.superbeta.emi.models.WallpaperDataModel
import com.superbeta.emi.utils.EmiDatabase

class WallpaperLocalDbService(private val db: EmiDatabase) : WallpaperLocalDao {

    override suspend fun saveWallpapersToDb(wallpapersList: List<WallpaperDataModel>) =
        db.wallpaperDao().saveWallpapersToDb(wallpapersList)


    override suspend fun getWallpapersFromDb(): List<WallpaperDataModel> =
        db.wallpaperDao().getWallpapersFromDb()

}