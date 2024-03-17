package com.superbeta.emi.home.data.local

import androidx.room.Room
import com.superbeta.emi.home.data.WallpaperDataModel
import com.superbeta.emi.utils.RoomInstance

class WallpaperLocalDbService(private val db: WallpaperDatabase) : WallpaperLocalDao {

    override fun saveWallpapersToDb(wallpapersList: List<WallpaperDataModel>) =
        db.wallpaperDao().saveWallpapersToDb(wallpapersList)


    override fun getWallpapersFromDb(): List<WallpaperDataModel> =
        db.wallpaperDao().getWallpapersFromDb()

}