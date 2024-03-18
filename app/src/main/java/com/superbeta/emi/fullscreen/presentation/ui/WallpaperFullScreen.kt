package com.superbeta.emi.fullscreen.presentation.ui

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.superbeta.emi.fullscreen.presentation.viewmodel.FullScreenViewModel
import com.superbeta.emi.fullscreen.repo.downloader.AndroidDownloader
import com.superbeta.emi.utils.SupabaseInstance.supabase
import com.superbeta.emi.models.WallpaperDataModel
import io.github.jan.supabase.exceptions.NotFoundRestException
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperFullScreen(
    navController: NavHostController,
    wallpaperId: Int,
    viewModel: FullScreenViewModel = viewModel(factory = FullScreenViewModel.Factory)
) {
    val context = LocalContext.current
    val downloader = AndroidDownloader(context)

    Scaffold { pd ->

        var wallpaper by remember {
            mutableStateOf<WallpaperDataModel?>(null)
        }

        LaunchedEffect(Unit) {
            withContext(IO) {
                try {
                    wallpaper = viewModel.getWallpaper(wallpaperId)

                } catch (e: NotFoundRestException) {
                    Log.e("itsivag", e.toString())
                }
            }
        }

//        if (wallpaper.isNotEmpty()) {
//            val currWallpaper = wallpaper[0]
        Box(
            modifier = Modifier.padding(pd),
        ) {

            AsyncImage(
                contentScale = ContentScale.Crop,
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        wallpaper?.thumbnailUrl
                    )
                    .crossfade(true)
                    .build(),
                contentDescription = null
            )
            Column(modifier = Modifier.padding(pd)) {
                TopAppBar(
                    title = { Text(text = wallpaper?.wallpaperName.toString()) },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "back"
                            )
                        }
                    })

                Row {
                    Button(onClick = {

                        CoroutineScope(IO).launch {
                            val wallpaperManager = WallpaperManager.getInstance(context)
                            if (wallpaperManager.isWallpaperSupported && wallpaperManager.isSetWallpaperAllowed) {
//                                    wallpaperManager.getCropAndSetWallpaperIntent(bitmap)
//                                    wallpaperManager.setBitmap(bitmap)
                            }
                        }
                    }) {
                        Text(text = "Set Wallpaper")
                    }
                    Button(onClick = {
                        wallpaper?.wallpaperUrl?.let {
                            wallpaper?.wallpaperName?.let { it1 ->
                                downloader.downloadFile(
                                    it,
                                    it1
                                )
                            }
                        }
                    }) {
                        Text(text = "Download Wallpaper")
                    }
                }
            }
        }
//        }
    }

}