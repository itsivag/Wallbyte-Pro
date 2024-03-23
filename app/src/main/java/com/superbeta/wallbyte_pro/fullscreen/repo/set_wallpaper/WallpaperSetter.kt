package com.superbeta.wallbyte_pro.fullscreen.repo.set_wallpaper

import android.app.WallpaperManager
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.superbeta.wallbyte_pro.models.WallpaperDataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class WallpaperSetter(private val context: Context) {

    fun setWallpaper(wallpaper: WallpaperDataModel) {
        CoroutineScope(IO).launch {
            val wallpaperManager = WallpaperManager.getInstance(context)
            if (wallpaperManager.isWallpaperSupported && wallpaperManager.isSetWallpaperAllowed) {
                val loader = ImageLoader(context)
                val request = ImageRequest.Builder(context)
                    .data(wallpaper.wallpaperUrl)
                    .allowHardware(false)
                    .build()

                try {

                    val result = (loader.execute(request) as SuccessResult).drawable
                    val bitmap = (result as BitmapDrawable).bitmap
                    wallpaperManager.setBitmap(bitmap)
                } catch (e: Exception) {
                    Log.e("Set Wallpaper", e.toString())
                }
            }
        }
    }
}