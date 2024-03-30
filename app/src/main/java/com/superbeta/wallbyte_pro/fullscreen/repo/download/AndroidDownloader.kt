package com.superbeta.wallbyte_pro.fullscreen.repo.download

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.core.net.toUri
import kotlinx.coroutines.delay


class AndroidDownloader(
    private val context: Context
) {
    fun downloadFile(url: String, wallpaperName: String, callback: (Boolean) -> Unit) {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val request = DownloadManager.Request(Uri.parse(url))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
            .setTitle("${wallpaperName.capitalize(Locale.current)} - WallbytePro")
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "${wallpaperName.capitalize(Locale.current)}-WallbytePro.jpg"
            )
            .setMimeType("image/*")

        try {
            downloadManager.enqueue(request)
            callback(true) // Download started successfully
        } catch (e: Exception) {
            Log.e("AndroidDownloader", "Error downloading file: $e")
            callback(false) // Download failed
        }
    }
}
