package com.superbeta.wallbyte_pro.utils

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun OnError(modifier: Modifier) {
    Text(text = "Failed to Load Wallpapers", modifier = modifier)
}

