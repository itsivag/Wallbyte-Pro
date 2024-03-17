package com.superbeta.emi.home.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.superbeta.emi.utils.SupabaseInstance.supabase
import com.superbeta.emi.home.data.WallpaperDataModel
import com.superbeta.emi.home.data.local.WallpaperLocalDbService
import com.superbeta.emi.utils.RoomInstance
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.NotFoundRestException
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val context = LocalContext.current
    val db = WallpaperLocalDbService(RoomInstance.getDb(context = context))

    var wallpapers by remember {
        mutableStateOf<List<WallpaperDataModel>>(listOf())
    }
    var isInternetConnected by remember {
        mutableStateOf(true)
    }


    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
                wallpapers =
//                    db.getWallpapersFromDb()
                    supabase.from("wallpaper").select().decodeList<WallpaperDataModel>()
            } catch (e: NotFoundRestException) {
                Log.e("itsivag", e.toString())
            } catch (e: HttpRequestException) {
                Log.e("itsivag", e.toString())
                isInternetConnected = false;
            }
            db.saveWallpapersToDb(wallpapers)
        }
    }

    if (isInternetConnected) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(
                start = 4.dp,
                top = 16.dp,
                end = 4.dp,
//            bottom = 4.dp
            ),
            content = {
                items(
                    items = wallpapers,
                    key = { wallpaper -> wallpaper.wallpaperId }) { wallpaper ->
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .height(screenHeight / 4),
                        onClick = { navController.navigate("wallpaperFullscreen/${wallpaper.wallpaperId}") }
                    ) {
                        AsyncImage(
                            contentScale = ContentScale.Crop,
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(wallpaper.thumbnailUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = null
                        )
                    }
                }
            }
        )
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "No Internet")
        }
    }


}