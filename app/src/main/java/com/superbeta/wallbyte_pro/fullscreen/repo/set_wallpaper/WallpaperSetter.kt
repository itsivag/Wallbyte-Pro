package com.superbeta.wallbyte_pro.fullscreen.repo.set_wallpaper

import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.superbeta.wallbyte_pro.models.WallpaperDataModel
import com.superbeta.wallbyte_pro.utils.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

enum class WallpaperSetType {
    SET_WALLPAPER,
    CROP_AND_SET_WALLPAPER
}

class WallpaperSetter(private val context: Context) {

    suspend fun setWallpaper(
        wallpaper: WallpaperDataModel,
        type: WallpaperSetType,
        callback: (Boolean) -> Unit
    ) {
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

                    when (type) {
                        WallpaperSetType.SET_WALLPAPER -> {
                            wallpaperManager.setBitmap(bitmap)
                            callback(true)
                            Toast.makeText(context, "Wallpaper Set", Toast.LENGTH_SHORT).show()
                        }

                        WallpaperSetType.CROP_AND_SET_WALLPAPER -> {
                            cropAndSetWallpaper(bitmap, wallpaper.wallpaperName.toString())
                            callback(true)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("Wallpaper Setter", "Error setting wallpaper: $e")
                    callback(false)
                }
            } else {
                callback(false)
            }
        }
    }


    private fun getImageUri(resource: Bitmap, wallpaperName: String): Uri? {
        val bytes = ByteArrayOutputStream()
        resource.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

        val path = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            resource,
            wallpaperName,
            "Downloaded From Wallbyte Pro"
        )

        return Uri.parse(path)
    }

    private fun cropAndSetWallpaper(bitmap: Bitmap, wallpaperName: String) {
        val intent = Intent(WallpaperManager.ACTION_CROP_AND_SET_WALLPAPER)
        val mime = "image/*";
        intent.setDataAndType(getImageUri(bitmap, wallpaperName), mime)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent)

        } catch (e: Exception) {
            Log.e("set wallpaper", e.toString())
            Toast.makeText(context, "Set Wallpaper Failed Try Downloading It", Toast.LENGTH_SHORT)
                .show()
        }
    }
}