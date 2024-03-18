package com.superbeta.emi.home.repo

import com.superbeta.emi.models.WallpaperDataModel
import com.superbeta.emi.home.data.local.WallpaperLocalDao
import com.superbeta.emi.home.data.remote.WallpaperRemoteDao

class WallpaperRepositoryImpl(
    private val wallpaperLocalDao: WallpaperLocalDao,
    private val wallpaperRemoteDao: WallpaperRemoteDao
) : WallpaperRepository {

    override suspend fun getWallpapers(): List<WallpaperDataModel> {
        val remoteList = wallpaperRemoteDao.getWallpapersFromRemote()
        val localList = wallpaperLocalDao.getWallpapersFromDb()
        if (remoteList.size != localList.size) {
            wallpaperLocalDao.saveWallpapersToDb(remoteList)
        }
        return wallpaperLocalDao.getWallpapersFromDb()
    }

}