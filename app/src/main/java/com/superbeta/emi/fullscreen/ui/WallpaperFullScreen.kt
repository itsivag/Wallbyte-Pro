package com.superbeta.emi.fullscreen.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.superbeta.emi.fullscreen.repo.downloader.AndroidDownloader
import com.superbeta.emi.utils.SupabaseInstance.supabase
import com.superbeta.emi.home.data.WallpaperDataModel
import io.github.jan.supabase.exceptions.NotFoundRestException
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperFullScreen(navController: NavHostController, wallpaperId: Int) {
    val context = LocalContext.current
    val downloader = AndroidDownloader(context)

    Scaffold { pd ->

        var wallpaper by remember {
            mutableStateOf<List<WallpaperDataModel>>(listOf())
        }

        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                try {
                    wallpaper =
                        supabase.from("wallpaper")
                            .select(columns = Columns.ALL) {
                                filter {
//                                    WallpaperDataModel::wallpaperId eq wallpaperId
                                    eq("id", wallpaperId)
                                }
                            }.decodeList<WallpaperDataModel>()

                } catch (e: NotFoundRestException) {
                    Log.e("itsivag", e.toString())
                }
            }
        }

        if (wallpaper.isNotEmpty()) {
            val currWallpaper = wallpaper[0]
            Box(
                modifier = Modifier.padding(pd),
            ) {

                AsyncImage(
                    contentScale = ContentScale.Crop,
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(
                            currWallpaper.thumbnailUrl
                        )
                        .crossfade(true)
                        .build(),
                    contentDescription = null
                )
                Column(modifier = Modifier.padding(pd)) {
                    TopAppBar(
                        title = { Text(text = currWallpaper.wallpaperName.toString()) },
                        navigationIcon = {
                            IconButton(
                                onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "back"
                                )
                            }
                        })

                    Button(onClick = {
                        currWallpaper.wallpaperUrl?.let {
                            currWallpaper.wallpaperName?.let { it1 ->
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
    }

}
