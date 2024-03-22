package com.superbeta.wallbyte_pro.models


import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Entity(tableName = "wallpaper")
@Immutable
@Stable
@Serializable
data class WallpaperDataModel(
    @PrimaryKey
    @SerialName("id")
    val wallpaperId: Int,

    @ColumnInfo(name = "date_uploaded")
    @SerialName("dateuploaded")
    val dateUploaded: String?,

    @ColumnInfo(name = "downloads")
    @SerialName("downloads")
    val downloads: Int?,


    @ColumnInfo(name = "image_size")
    @SerialName("imagesize")
    val wallpaperSize: String?,

    @ColumnInfo(name = "image_url")
    @SerialName("imageurl")
    val wallpaperUrl: String?,

    @ColumnInfo(name = "thumbnail_url")
    @SerialName("thumbnailurl")
    val thumbnailUrl: String?,

    @ColumnInfo(name = "wallpaper_category")
    @SerialName("wallpapercategory")
    val wallpaperCategory: String?,

    @ColumnInfo(name = "wallpaper_name")
    @SerialName("wallpapername")
    val wallpaperName: String?
)