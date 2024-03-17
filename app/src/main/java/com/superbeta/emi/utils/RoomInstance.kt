package com.superbeta.emi.utils

import android.content.Context
import androidx.room.Room
import com.superbeta.emi.home.data.local.WallpaperDatabase

object RoomInstance {
    fun getDb(context: Context): WallpaperDatabase {
        return Room.databaseBuilder(
            context,
            WallpaperDatabase::class.java, "database-name"
        ).build()
    }
}