package com.superbeta.emi.utils

import android.content.Context
import androidx.room.Room

object RoomInstance {
    fun getDb(context: Context): EmiDatabase {
        return Room.databaseBuilder(
            context,
            EmiDatabase::class.java, "database-name"
        ).fallbackToDestructiveMigration().build()
    }
}