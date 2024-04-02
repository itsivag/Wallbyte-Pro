package com.superbeta.wallbyte_pro.home.data.remote

import android.util.Log
import com.superbeta.wallbyte_pro.models.WallpaperDataModel
import com.superbeta.wallbyte_pro.utils.SupabaseInstance.supabase
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order

class WallpaperRemoteDbService : WallpaperRemoteDao {
    override suspend fun getWallpapersFromRemote(page: Int): List<WallpaperDataModel> {
        val id = getWallpaperId()

        return try {
            supabase.from("wallpaper").select()
            {
                filter {
                    lte("id", id - page * 21)
                }
                limit(21)
                order(column = "id", order = Order.DESCENDING)
            }.decodeList<WallpaperDataModel>()

        } catch (e: Exception) {
            Log.e("Supabase Exception", e.toString())
            emptyList()
        }
    }

    override suspend fun getWallpaperId(): Int =
        try {
            supabase.from("wallpaper").select {
                limit(1)
                order(column = "id", order = Order.DESCENDING)
            }.decodeSingle<WallpaperDataModel>().wallpaperId
        } catch (e: Exception) {
            Log.e("Supabase Exception", e.toString())
            600
        }

}