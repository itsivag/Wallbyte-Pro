package com.superbeta.wallbyte_pro.fullscreen.repo.set_wallpaper

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi

class SetWallpaperBroadCastReceiver : BroadcastReceiver() {
    private lateinit var downloadManager: DownloadManager
    private lateinit var wallpaperManager: WallpaperManager

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            downloadManager = context.getSystemService(DownloadManager::class.java)
            wallpaperManager = WallpaperManager.getInstance(context)
        }
        if (intent?.action == "android.intent.action.SET_WALLPAPER") {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
            if (id != -1L) {
                print("Download with id $id Finished")
                Toast.makeText(context, "Set Wallpaper", Toast.LENGTH_SHORT).show()
                val uri = downloadManager.getUriForDownloadedFile(id)
                context!!.startActivity(
                    Intent(wallpaperManager.getCropAndSetWallpaperIntent(uri))
                )
            }
        }
    }

}