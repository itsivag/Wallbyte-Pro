package com.superbeta.wallbyte_pro.home.repo

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.flatMap
import androidx.paging.map
import com.superbeta.wallbyte_pro.home.data.local.WallpaperLocalDao
import com.superbeta.wallbyte_pro.home.data.remote.WallpaperRemoteDao
import com.superbeta.wallbyte_pro.models.WallpaperDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WallpaperRepositoryImpl(
    private val wallpaperLocalDao: WallpaperLocalDao,
    private val wallpaperRemoteDao: WallpaperRemoteDao
) : WallpaperRepository {

    override suspend fun getWallpapers(page: Int): List<WallpaperDataModel> {
        val remoteList = wallpaperRemoteDao.getWallpapersFromRemote(page)
        val localList = wallpaperLocalDao.getWallpapersFromDb()
        if (remoteList.firstOrNull() != localList.firstOrNull()) {
            wallpaperLocalDao.saveWallpapersToDb(remoteList)
        }

        return wallpaperLocalDao.getWallpapersFromDb()
//        return remoteList
    }

}