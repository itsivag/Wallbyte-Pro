package com.superbeta.wallbyte_pro.category

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {
    private val _categoryList = mutableStateOf<List<CategoryModel>>(emptyList())
    val categoryList = _categoryList

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {

            val categoryList = arrayListOf<CategoryModel>()


            categoryList.add(
                CategoryModel(
                    "https://firebasestorage.googleapis.com/v0/b/wallpei.appspot.com/o/categories%2Fcat1.webp?alt=media&token=ec800766-5c73-40b3-9456-101653f990d6",
                    "Abstract"
                )
            )
            categoryList.add(
                CategoryModel(
                    "https://firebasestorage.googleapis.com/v0/b/wallpei.appspot.com/o/categories%2Fcat2.webp?alt=media&token=5490aa8b-db02-4e46-8d22-9226f20498f0",
                    "Amoled"
                )
            )

            categoryList.add(
                CategoryModel(
                    "https://firebasestorage.googleapis.com/v0/b/wallpei.appspot.com/o/categories%2Fcat3.webp?alt=media&token=40cac719-dfd9-4a7a-a756-5c142d87843f",
                    "Anime"
                )
            )

            categoryList.add(
                CategoryModel(
                    "https://firebasestorage.googleapis.com/v0/b/wallpei.appspot.com/o/categories%2Fcat4.webp?alt=media&token=10da87eb-f740-4fc0-a362-83486b44ce2e",
                    "Games"
                )
            )

            categoryList.add(
                CategoryModel(
                    "https://firebasestorage.googleapis.com/v0/b/wallpei.appspot.com/o/categories%2Fcat5.webp?alt=media&token=e12dcd11-410f-49c7-b298-2e556f3f681f",
                    "Minimal"
                )
            )

            categoryList.add(
                CategoryModel(
                    "https://firebasestorage.googleapis.com/v0/b/wallpei.appspot.com/o/categories%2Fcat16.webp?alt=media&token=6b2cf666-d85f-4fed-9815-dba571771350",
                    "Nature"
                )
            )

            categoryList.add(
                CategoryModel(
                    "https://firebasestorage.googleapis.com/v0/b/wallpei.appspot.com/o/categories%2Fcat7.webp?alt=media&token=4f6f874e-dbed-4a5d-a8b4-0dde931c693b",
                    "Series"
                )
            )

            categoryList.add(
                CategoryModel(
                    "https://firebasestorage.googleapis.com/v0/b/wallpei.appspot.com/o/categories%2Fcat8.webp?alt=media&token=f764e3ca-4e75-450e-8707-9ff6ecc8177f",
                    "Movies"
                )
            )

            categoryList.add(
                CategoryModel(
                    "https://firebasestorage.googleapis.com/v0/b/wallpei.appspot.com/o/categories%2Fcat9.webp?alt=media&token=ab17b116-4416-43be-9750-70f75abc0601",
                    "Sports"
                )
            )

            categoryList.add(
                CategoryModel(
                    "https://firebasestorage.googleapis.com/v0/b/wallpei.appspot.com/o/categories%2Fcat10.webp?alt=media&token=00a7506a-2c5c-4db5-9054-74fc2f0a3ac1",
                    "Stock"
                )
            )
            _categoryList.value = categoryList
        }


    }
}
