package com.superbeta.emi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.superbeta.emi.fullscreen.presentation.ui.WallpaperFullScreen
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
            HomeScreen(navController)
        }
        composable(
            "wallpaperFullscreen/{wallpaperId}",
            arguments = listOf(navArgument("wallpaperId") { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.let {
                WallpaperFullScreen(
                    navController,
                    it.getInt("wallpaperId")
                )
            }
        }
    }
}

