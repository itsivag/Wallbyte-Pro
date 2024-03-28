package com.superbeta.wallbyte_pro.fullscreen.repo.download

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.core.net.toUri


class AndroidDownloader(
    context: Context
) : Downloader {

    private val downloadManager: DownloadManager =
        context.getSystemService(DownloadManager::class.java)

    override fun downloadFile(url: String, wallpaperName: String): Long {

        val req = DownloadManager.Request(url.toUri())
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
            .setTitle("${wallpaperName.capitalize(Locale.current)}-WallbytePro")
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "${wallpaperName.capitalize(Locale.current)}-WallbytePro.jpg"
            )
            .setMimeType("image/*")

        return downloadManager.enqueue(req)
    }

}