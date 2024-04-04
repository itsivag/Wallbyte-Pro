// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.devtools.ksp) apply false
//    alias(libs.plugins.dagger.hilt) apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
}