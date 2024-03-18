package com.superbeta.emi.fullscreen.data.local

import com.superbeta.emi.models.WallpaperDataModel
import com.superbeta.emi.utils.EmiDatabase

class FullScreenLocalService(private val db: EmiDatabase) : FullScreenLocalDao {

    override suspend fun getWallpaperFromDb(wallId: Int): WallpaperDataModel =
        db.fullScreenDao().getWallpaperFromDb(wallId)


}