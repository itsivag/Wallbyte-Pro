package com.superbeta.wallbyte_pro.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import com.superbeta.wallbyte_pro.fullscreen.data.local.FullScreenLocalDao
import com.superbeta.wallbyte_pro.models.WallpaperDataModel
import com.superbeta.wallbyte_pro.home.data.local.WallpaperLocalDao

@Database(entities = [WallpaperDataModel::class], version = 2)
abstract class EmiDatabase : RoomDatabase() {
    abstract fun wallpaperDao(): WallpaperLocalDao
    abstract fun fullScreenDao(): FullScreenLocalDao
}