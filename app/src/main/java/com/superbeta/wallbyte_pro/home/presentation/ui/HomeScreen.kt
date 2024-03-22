package com.superbeta.wallbyte_pro.home.presentation.ui

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
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.superbeta.wallbyte_pro.models.WallpaperDataModel
import com.superbeta.wallbyte_pro.home.presentation.viewmodel.WallpaperViewModel
import com.superbeta.wallbyte_pro.utils.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: WallpaperViewModel = viewModel(factory = WallpaperViewModel.Factory),
) {
    val wallpapers by viewModel.wallpaperState.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text(text = "App") }) }) {
        val modifier = Modifier.padding(it)
        when (uiState) {
            is UiState.Error -> OnError(modifier)
            UiState.Loading -> OnLoading(modifier)
            UiState.Success -> OnSuccess(wallpapers, navController, modifier, viewModel)
        }
    }

}

@Composable
fun OnError(modifier: Modifier) {
    Text(text = "Failed to Load Wallpapers", modifier = modifier)
}

@Composable
fun OnLoading(modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnSuccess(
    wallpapers: List<WallpaperDataModel>,
    navController: NavController,
    modifier: Modifier,
    viewModel: WallpaperViewModel,
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(
            start = 4.dp, top = 16.dp, end = 4.dp, bottom = 4.dp
        ),
        content = {
            items(items = wallpapers, key = { wallpaper -> wallpaper.wallpaperId }) { wallpaper ->
                Card(modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .height(screenHeight / 4),
                    onClick = { navController.navigate("wallpaperFullscreen/${wallpaper.wallpaperId}") }) {
                    AsyncImage(
                        contentScale = ContentScale.Crop,
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(wallpaper.thumbnailUrl)
                            .crossfade(true).build(),
                        contentDescription = null
                    )
                }
            }

            item {
                Log.e("scroll", "Scrolled to Bottom")
                LaunchedEffect(true) {
                    CoroutineScope(IO).launch {
                        viewModel.getWallpapers()
                    }
                }
                if (uiState == UiState.Loading) {
                    OnLoading(Modifier)
                }
            }
        }
    )
}