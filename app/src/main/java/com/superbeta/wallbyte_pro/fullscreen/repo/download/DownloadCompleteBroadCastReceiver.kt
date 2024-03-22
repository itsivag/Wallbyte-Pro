package com.superbeta.wallbyte_pro.fullscreen.repo.download

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class DownloadCompleteBroadCastReceiver : BroadcastReceiver() {
    private lateinit var downloadManager: DownloadManager
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            downloadManager = context.getSystemService(DownloadManager::class.java)
        }
        if (intent?.action == "android.intent.action.DOWNLOAD_COMPLETE") {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
            if (id != -1L) {
                print("Download with id $id Finished")
                Toast.makeText(context, "Download Complete", Toast.LENGTH_SHORT).show()
//                val uri = downloadManager.getUriForDownloadedFile(id)
            }
        }
    }

}