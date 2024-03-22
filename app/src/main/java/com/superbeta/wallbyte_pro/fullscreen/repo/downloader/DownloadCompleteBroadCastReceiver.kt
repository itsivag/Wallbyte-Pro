package com.superbeta.wallbyte_pro.fullscreen.repo.downloader

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
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
            }
        }
    }

    private fun getDownloadUri(context: Context, downloadId: Long): Uri? {
        val query = DownloadManager.Query().apply {
            setFilterById(downloadId)
        }
        val cursor = downloadManager.query(query)
        if (cursor.moveToFirst()) {
            val uriIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)
            val uriString = cursor.getString(uriIndex)
            cursor.close()
            return Uri.parse(uriString)
        }
        cursor.close()
        return null
    }
}