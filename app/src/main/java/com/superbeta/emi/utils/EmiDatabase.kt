package com.superbeta.emi.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import com.superbeta.emi.fullscreen.data.local.FullScreenLocalDao
import com.superbeta.emi.models.WallpaperDataModel
import com.superbeta.emi.home.data.local.WallpaperLocalDao

@Database(entities = [WallpaperDataModel::class], version = 2)
abstract class EmiDatabase : RoomDatabase() {
    abstract fun wallpaperDao(): WallpaperLocalDao
    abstract fun fullScreenDao(): FullScreenLocalDao
}