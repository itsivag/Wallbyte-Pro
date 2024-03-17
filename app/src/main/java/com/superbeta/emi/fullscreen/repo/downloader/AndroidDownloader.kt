package com.superbeta.emi.fullscreen.repo.downloader

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri

class AndroidDownloader(
    private val context: Context
) : Downloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)
    override fun downloadFile(url: String, wallpaperName: String): Long {
        val req = DownloadManager.Request(url.toUri()).setMimeType("image/*")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
            .setTitle("Wallpaper Download Complete")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, wallpaperName)
        return downloadManager.enqueue(req)
    }
}