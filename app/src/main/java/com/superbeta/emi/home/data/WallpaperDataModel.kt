package com.superbeta.emi.home.data


import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Stable
@Serializable
data class WallpaperDataModel(
    @SerialName("dateuploaded")
    val dateUploaded: String?,
    @SerialName("downloads")
    val downloads: Int?,
    @SerialName("id")
    val wallpaperId: Int,
    @SerialName("imagesize")
    val wallpaperSize: String?,
    @SerialName("imageurl")
    val wallpaperUrl: String?,
    @SerialName("thumbnailurl")
    val thumbnailUrl: String?,
    @SerialName("wallpapercategory")
    val wallpaperCategory: String?,
    @SerialName("wallpapername")
    val wallpaperName: String?
)