package com.superbeta.wallbyte_pro.fullscreen.repo.download

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.util.Log
import androidx.core.net.toUri


class AndroidDownloader(
    private val context: Context
) : Downloader {

    private val downloadManager: DownloadManager =
        context.getSystemService(DownloadManager::class.java)

    override fun downloadFile(url: String, wallpaperName: String): Long {
        val req = DownloadManager.Request(url.toUri()).setMimeType("image/*")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
            .setTitle("Wallpaper Download Complete")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, wallpaperName)
        return downloadManager.enqueue(req)
    }

    override fun setWallpaper(url: String, wallpaperName: String): Long {
        Log.i("set wallpaper", "Wallpaper URL : $url")
        val req = DownloadManager.Request(url.toUri()).setMimeType("image/*")
//            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
//            .setTitle("Wallpaper Download Complete")
//            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, wallpaperName)
//        val intent = Intent("android.intent.action.SET_WALLPAPER")
//        context.sendBroadcast(intent)
//        Log.i("Set Wallpaper", "Set Wallpaper Broadcast Sent")
        return downloadManager.enqueue(req)

    }


}