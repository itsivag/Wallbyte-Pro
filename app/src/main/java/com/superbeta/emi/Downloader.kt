package com.superbeta.emi

interface Downloader {
    fun downloadFile(url: String, wallpaperName: String): Long
}