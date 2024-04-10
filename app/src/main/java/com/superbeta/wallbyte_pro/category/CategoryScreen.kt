package com.superbeta.wallbyte_pro.category

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CategoryScreen(navController: NavHostController, viewModel: CategoryViewModel = viewModel()) {
    val categoryList = remember {
        mutableStateOf<List<CategoryModel>>(emptyList())
    }
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            categoryList.value = viewModel.categoryList.value
        }
    }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    LazyColumn {
        items(count = categoryList.value.size) { i ->
            val category = categoryList.value[i]


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight / 5)
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                onClick = { navController.navigate("category/${category.categoryName}") }
            ) {
                Box {
                    AsyncImage(
                        contentScale = ContentScale.Crop,
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(category.imageUrl).crossfade(true).build(),
                        contentDescription = "Category name : " + category.categoryName
                    )

                    Text(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(8.dp),
                        color = Color.White,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.2f), blurRadius = 5f
                            )
                        ),
                        text = categoryList.value[i].categoryName,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 28.sp
                    )
                }
            }
        }
    }
}

data class CategoryModel(
    val imageUrl: String = "", val categoryName: String = ""
)

