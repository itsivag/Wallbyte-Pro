package com.superbeta.wallbyte_pro

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.superbeta.wallbyte_pro.navigation.EmiNavHost
import com.superbeta.wallbyte_pro.ui.theme.EmiTheme
import com.superbeta.wallbyte_pro.ui.theme.SusaFamily

class MainActivity : ComponentActivity() {
    data class BottomBarItem(
        val title: String,
        val selectedIcon: ImageVector,
        val unselectedIcon: ImageVector,
        val badgeAmount: Int? = null
    )

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmiTheme {
                val homeTab = BottomBarItem(
                    title = "home",
                    selectedIcon = Icons.Default.Home,
                    unselectedIcon = Icons.Outlined.Home
                )
                val categoriesTab = BottomBarItem(
                    title = "categories",
                    selectedIcon = ImageVector.vectorResource(id = R.drawable.category),
                    unselectedIcon = ImageVector.vectorResource(id = R.drawable.category),
                )

                val tabBarItems = listOf(homeTab, categoriesTab)
                var isMenuExpanded by remember { mutableStateOf(false) }
                val context = LocalContext.current


                val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
                val topBarState = rememberSaveable { (mutableStateOf(true)) }

                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    val navBackStackEntry by navController.currentBackStackEntryAsState()

                    when (navBackStackEntry?.destination?.route) {
                        "home" -> {
                            bottomBarState.value = true
                            topBarState.value = true
                        }

                        "categories" -> {
                            bottomBarState.value = true
                            topBarState.value = true
                        }

                        else -> {
                            bottomBarState.value = false
                            topBarState.value = false
                        }
                    }
                    Scaffold(
                        topBar = {
                            if (topBarState.value)
                                TopAppBar(
                                    title = {
                                        Text(
                                            text = "Wallbyte Pro",
                                            fontFamily = SusaFamily,
                                            fontWeight = FontWeight.Bold
                                        )
                                    },
                                    actions = {
                                        IconButton(onClick = { isMenuExpanded = !isMenuExpanded }) {
                                            Icon(
                                                imageVector = Icons.Outlined.MoreVert,
                                                contentDescription = "Menu"
                                            )
                                        }

                                        DropdownMenu(expanded = isMenuExpanded,
                                            onDismissRequest = { isMenuExpanded = false }) {
//                                    DropdownMenuItem(text = { Text("Refresh") }, onClick = {
//                                        CoroutineScope(Dispatchers.IO).launch {
//                                            viewModel.refreshWallpapersList()
//                                        }
//                                    })
                                            DropdownMenuItem(text = { Text("Share") }, onClick = {
                                                val sendIntent: Intent = Intent().apply {
                                                    action = Intent.ACTION_SEND
                                                    putExtra(
                                                        Intent.EXTRA_TEXT,
                                                        "https://play.google.com/store/apps/details?id=com.superbeta.wallbyte_pro"
                                                    )
                                                    type = "text/html"
                                                }

                                                val shareIntent = Intent.createChooser(
                                                    sendIntent, "Share Wallbyte Pro with Friends❤️"
                                                )
                                                context.startActivity(shareIntent)
                                            })
                                        }
                                    },
                                )


                        }, bottomBar = {

                            if (bottomBarState.value)
                                TabView(
                                    tabBarItems,
                                    navController,
                                    tabBarItems.indexOfFirst { it.title == navBackStackEntry?.destination?.route })
                        }) {
                        EmiNavHost(navController = navController, modifier = Modifier.padding(it))
                    }
                }
            }
        }
    }

}

@Composable
fun TabView(
    bottomBarItems: List<MainActivity.BottomBarItem>,
    navController: NavController,
    initialSelectedIndex: Int
) {

    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(initialSelectedIndex) // Initialize selectedTabIndex with the provided initial index
    }

    NavigationBar {
        // looping over each tab to generate the views and navigation for each item
        bottomBarItems.forEachIndexed { index, tabBarItem ->
            NavigationBarItem(selected = selectedTabIndex == index, onClick = {
                selectedTabIndex = index
                navController.navigate(tabBarItem.title)
            }, icon = {
                TabBarIconView(
                    isSelected = selectedTabIndex == index,
                    selectedIcon = tabBarItem.selectedIcon,
                    unselectedIcon = tabBarItem.unselectedIcon,
                    title = tabBarItem.title,
                    badgeAmount = tabBarItem.badgeAmount
                )
            }, label = { Text(tabBarItem.title) })
        }
    }
}

@Composable
fun TabBarIconView(
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    title: String,
    badgeAmount: Int? = null
) {
//    BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
    Icon(
        imageVector = if (isSelected) {
            selectedIcon
        } else {
            unselectedIcon
        }, contentDescription = title
    )
//    }
}
