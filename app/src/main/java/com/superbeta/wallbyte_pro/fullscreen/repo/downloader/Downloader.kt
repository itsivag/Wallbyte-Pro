package com.superbeta.wallbyte_pro.fullscreen.repo.downloader

interface Downloader {
    fun downloadFile(url: String, wallpaperName: String): Long
}