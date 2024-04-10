package com.superbeta.wallbyte_pro.category_fullscreen

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.superbeta.wallbyte_pro.home.presentation.ui.WallpaperListItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CategoryFullScreen(
    navController: NavHostController,
    categoryName: String?,
    viewModel: CategoryFullScreenViewModel = viewModel(factory = CategoryFullScreenViewModel.Factory)
) {

    val wallpapers by viewModel.wallpaperState.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        CoroutineScope(IO).launch {
            categoryName?.let { it1 -> viewModel.getWallpapersByCategory(it1) }
        }
    }


    Scaffold {
        LazyVerticalGrid(
            modifier = Modifier.padding(it),
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(
                start = 4.dp, top = 0.dp, end = 4.dp, bottom = 0.dp
            ),
            content = {
                items(
                    items = wallpapers,
                    key = { wallpaper -> wallpaper.wallpaperId }) { wallpaper ->
                    WallpaperListItem(wallpaper, navController)
                }

            }
        )
//    Text(text = "Category: $categoryName")
    }
}

