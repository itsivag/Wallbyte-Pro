package com.superbeta.wallbyte_pro.fullscreen.repo.download

interface Downloader {
    fun downloadFile(url: String, wallpaperName: String): Long
    fun setWallpaper(url: String, wallpaperName: String): Long
}