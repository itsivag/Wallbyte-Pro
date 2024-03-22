package com.superbeta.wallbyte_pro.home.data.remote

import android.util.Log
import com.superbeta.wallbyte_pro.models.WallpaperDataModel
import com.superbeta.wallbyte_pro.utils.SupabaseInstance.supabase
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order

class WallpaperRemoteDbService : WallpaperRemoteDao {
    override suspend fun getWallpapersFromRemote(page: Int): List<WallpaperDataModel> =
        try {
            supabase.from("wallpaper").select()
            {
                filter {
                    lt("id", 604 - page * 21)
                }
                limit(21)
                order(column = "id", order = Order.DESCENDING)
            }.decodeList<WallpaperDataModel>()

        } catch (e: Exception) {
            Log.e("Supabase Exception", e.toString())
            emptyList()
        }
}