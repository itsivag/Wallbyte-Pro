package com.superbeta.emi.home.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.superbeta.emi.home.data.WallpaperDataModel

@Database(entities = [WallpaperDataModel::class], version = 1)
abstract class WallpaperDatabase : RoomDatabase() {
    abstract fun wallpaperDao(): WallpaperLocalDao
}