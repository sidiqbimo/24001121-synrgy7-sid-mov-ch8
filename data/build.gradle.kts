plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")

    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
}

android {
    namespace = "com.bimobelajar.mymovie.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        targetSdk = 34
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":core"))
    implementation(libs.kotlinStdlib)
    implementation(libs.androidx.core.ktx)
    implementation(libs.retrofit2.retrofit)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.androidx.room.runtime)


    implementation(libs.chucker)


    implementation(libs.work.runtime.ktx)

    implementation(libs.kotlinStdlib)
}
