package com.superbeta.wallbyte_pro.category_fullscreen

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.superbeta.wallbyte_pro.models.WallpaperDataModel
import com.superbeta.wallbyte_pro.utils.OnError
import com.superbeta.wallbyte_pro.utils.OnLoading
import com.superbeta.wallbyte_pro.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
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


    Scaffold(
        topBar = {
            TopAppBar(
                title = { categoryName?.let { Text(text = it) } },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        val modifier = Modifier.padding(it)

        when (uiState) {
            is UiState.Error -> OnError(modifier)
            UiState.Loading -> OnLoading(modifier)
            UiState.Success -> CategoryFullScreenOnSuccess(
                wallpapers,
                navController,
                modifier,
                viewModel,
                categoryName
            )
        }

//    Text(text = "Category: $categoryName")
    }
}

@Composable
fun CategoryFullScreenOnSuccess(
    wallpapers: List<WallpaperDataModel>,
    navController: NavHostController,
    modifier: Modifier,
    viewModel: CategoryFullScreenViewModel,
    categoryName: String?
) {
    LazyVerticalGrid(
        modifier = modifier,
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

            item {
                Log.e("scroll", "Scrolled to Bottom")
                LaunchedEffect(true) {
                    CoroutineScope(IO).launch {
                        categoryName?.let { it1 -> viewModel.getWallpapersByCategory(it1) }
                    }
                }
//                if (viewModel.uiState.value == UiState.Loading) {
//                    CircularProgressIndicator()
//                }
            }

        }
    )
}