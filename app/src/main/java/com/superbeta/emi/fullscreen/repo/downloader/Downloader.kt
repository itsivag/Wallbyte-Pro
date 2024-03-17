package com.superbeta.emi.fullscreen.repo.downloader

interface Downloader {
    fun downloadFile(url: String, wallpaperName: String): Long
}