package com.superbeta.emi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.superbeta.emi.fullscreen.ui.WallpaperFullScreen
import com.superbeta.emi.home.presentation.ui.HomeScreen

@Composable
fun EmiNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("home") {
            HomeScreen { navController.navigate("wallpaperFullscreen") }
        }
        composable("wallpaperFullscreen") {
            WallpaperFullScreen(navController)
        }
    }
}

