package com.superbeta.emi.home.data.remote

import com.superbeta.emi.home.data.WallpaperDataModel
import com.superbeta.emi.utils.SupabaseInstance.supabase
import io.github.jan.supabase.postgrest.from

class WallpaperRemoteDbService : WallpaperRemoteDao {
    override suspend fun getWallpapersFromRemote(): List<WallpaperDataModel> =
        supabase.from("wallpaper").select().decodeList<WallpaperDataModel>()

}