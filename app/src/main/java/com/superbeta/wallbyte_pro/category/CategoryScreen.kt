package com.superbeta.wallbyte_pro.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
                    .height(screenHeight / 4)
                    .padding(horizontal = 8.dp, vertical = 10.dp)
            ) {
                Box {
                    AsyncImage(
                        contentScale = ContentScale.Crop,
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(category.imageUrl)
                            .crossfade(true).build(),
                        contentDescription = "Category name : " + category.categoryName
                    )

                    Text(text = categoryList.value[i].categoryName)
                }
            }
        }
    }
}

data class CategoryModel(
    val imageUrl: String = "",
    val categoryName: String = ""
)

