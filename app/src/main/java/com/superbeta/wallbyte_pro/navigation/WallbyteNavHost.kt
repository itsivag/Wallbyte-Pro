package com.superbeta.wallbyte_pro.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.superbeta.wallbyte_pro.category.CategoryScreen
import com.superbeta.wallbyte_pro.category_fullscreen.CategoryFullScreen
import com.superbeta.wallbyte_pro.fullscreen.presentation.ui.WallpaperFullScreen
import com.superbeta.wallbyte_pro.home.presentation.ui.HomeScreen

@Composable
fun EmiNavHost(
    navController: NavHostController,
    modifier: Modifier,
    startDestination: String = "home"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        composable("home") {
            HomeScreen(navController, modifier = modifier)
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
        composable("categories") {
            CategoryScreen(navController)
        }

        composable("category/{categoryName}") { backStackEntry ->
            backStackEntry.arguments?.let {
                CategoryFullScreen(navController, it.getString("categoryName"))
            }
        }

    }
}


