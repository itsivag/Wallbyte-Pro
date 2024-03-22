package com.superbeta.wallbyte_pro.fullscreen.data.local

import com.superbeta.wallbyte_pro.models.WallpaperDataModel
import com.superbeta.wallbyte_pro.utils.EmiDatabase

class FullScreenLocalService(private val db: EmiDatabase) : FullScreenLocalDao {

    override suspend fun getWallpaperFromDb(wallId: Int): WallpaperDataModel =
        db.fullScreenDao().getWallpaperFromDb(wallId)


}