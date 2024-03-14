package com.superbeta.emi.home.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.superbeta.emi.SupabaseInstance.supabase
import com.superbeta.emi.home.data.WallpaperDataModel
import io.github.jan.supabase.exceptions.NotFoundRestException
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigateToWallpaperFullscreen: () -> Unit) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

//    Scaffold(
//        topBar = { TopAppBar(title = { Text(text = "Emi") }) }
//    ) { pd ->
//        val list = (1..100).map { it.toString() }
//
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(3),
//            modifier = Modifier.padding(pd),
//            // content padding
//            contentPadding = PaddingValues(
//                start = 12.dp,
//                top = 16.dp,
//                end = 12.dp,
//                bottom = 16.dp
//            ),
//            content = {
//                items(list.size) { index ->
//                    Card(
////                        backgroundColor = Color.Red,
//                        modifier = Modifier
//                            .padding(4.dp)
//                            .fillMaxWidth()
//                            .height(screenHeight / 3),
////                        elevation = 8.dp,
//                    ) {
//                        Text(
//                            text = list[index],
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 30.sp,
//                            color = Color(0xFFFFFFFF),
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier.padding(16.dp)
//                        )
//                    }
//                }
//            }
//        )
//    }

    WallpaperList()
}

@Composable
fun WallpaperList() {

    var wallpapers by remember {
        mutableStateOf<List<WallpaperDataModel>>(listOf())
    }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
                wallpapers =
                    supabase.from("wallpaper").select().decodeList<WallpaperDataModel>()
            } catch (e: NotFoundRestException) {
                Log.e("itsivag", e.toString())
            }
        }
    }

    LazyColumn {
        items(wallpapers, key = { wallpaper -> wallpaper.wallpaperId }) { currWallpaper ->
            Text(
                currWallpaper.wallpaperId.toString() + currWallpaper.wallpaperName.toString(),
                modifier = Modifier.padding(8.dp),
            )
        }
    }

}