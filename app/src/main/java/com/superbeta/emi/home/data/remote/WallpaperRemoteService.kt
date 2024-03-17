package com.superbeta.emi.home.data.remote

import com.superbeta.emi.home.data.WallpaperDataModel

interface WallpaperRemoteService {
    fun getWallpapersFromRemote(): List<WallpaperDataModel>
}