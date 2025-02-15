// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("com.google.firebase.firebase-perf") version "1.4.2" apply false
}

buildscript {
    repositories {
        google()
    }
    dependencies {
        val nav_version = "2.7.7"
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
    }
}