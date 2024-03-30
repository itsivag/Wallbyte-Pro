package com.superbeta.wallbyte_pro.fullscreen.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.superbeta.wallbyte_pro.fullscreen.presentation.viewmodel.FullScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.superbeta.wallbyte_pro.R
import com.superbeta.wallbyte_pro.models.WallpaperDataModel
import com.superbeta.wallbyte_pro.utils.OnError
import com.superbeta.wallbyte_pro.utils.OnLoading
import com.superbeta.wallbyte_pro.utils.UiState

@Composable
fun WallpaperFullScreen(
    navController: NavHostController,
    wallpaperId: Int,
    viewModel: FullScreenViewModel = viewModel(factory = FullScreenViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val wallpaper by viewModel.wallpaperState.collectAsStateWithLifecycle()

    Scaffold { pd ->
        LaunchedEffect(Unit) {
            withContext(IO) {
                try {
                    viewModel.getWallpaper(wallpaperId)

                } catch (e: Exception) {
                    Log.e("Fullscreen Error", e.toString())
                }
            }
        }
        val modifier = Modifier.padding(pd)
        if (uiState == UiState.Error("")) {
            OnError(modifier = modifier)
        } else {
            FullScreenSuccess(modifier, wallpaper, navController, viewModel, uiState)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenSuccess(
    modifier: Modifier,
    wallpaper: WallpaperDataModel?,
    navController: NavHostController,
    viewModel: FullScreenViewModel,
    uiState: UiState
) {
    Box(
        modifier = modifier,
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
        Column(modifier = modifier) {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black.copy(alpha = 0.2f)
                ),
                title = {
                    Text(
                        text = wallpaper?.wallpaperName.toString().capitalize(Locale.current),
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back",
                            tint = Color.White
                        )
                    }
                })

            Spacer(modifier = Modifier.weight(1f))

            Row {
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.padding(16.dp)
                ) {
                    FloatingActionButton(onClick = {
                        CoroutineScope(IO).launch {
                            viewModel.downloadWallpaper()
                        }
                    }, modifier = Modifier.padding(8.dp)) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowForward,
                            modifier = Modifier.rotate(90f),
                            contentDescription = "Download"
                        )
                    }

                    FloatingActionButton(
                        onClick = {
                            CoroutineScope(IO).launch {
                                viewModel.setWallpaper()
                            }
                        },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.wallpaper),
                            contentDescription = "Set Wallpaper"
                        )
                    }


                    FloatingActionButton(
                        onClick = {
                            CoroutineScope(IO).launch {
                                viewModel.cropAndSetWallpaper()
                            }
                        },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.crop_and_set),
                            contentDescription = "Crop And Set Wallpaper"
                        )
                    }
                }
            }
        }

        if (uiState == UiState.Loading) {
            OnLoading(modifier = Modifier)
        }
    }
}
