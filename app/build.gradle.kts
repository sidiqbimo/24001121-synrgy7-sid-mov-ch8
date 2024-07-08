plugins {
    id("com.android.application")
    id("kotlin-android")
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.bimobelajar.mymovie"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bimobelajar.mymovie"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    flavorDimensions += "version"
    productFlavors {
        create("free") {
            dimension = "version"
            applicationIdSuffix = ".free"
            versionNameSuffix = "-free"
        }
        create("premium") {
            dimension = "version"
            applicationIdSuffix = ".prem"
            versionNameSuffix = "-prem"
        }
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":presentation"))
    implementation(project(":core"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.converter.gson)
    implementation(libs.squareup.picasso)
    implementation(libs.github.glide)
    implementation(libs.androidx.espresso.idling.resource)
    annotationProcessor(libs.glide.compiler)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.datastore.preferences)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.work.work.runtime.ktx)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.fragment.ktx)

    implementation(libs.espressoIntents)
    implementation(libs.espressoContrib)
    implementation(libs.androidx.espresso.core)
    implementation(libs.hamacrestLibrary)

    debugImplementation(libs.chucker)
    releaseImplementation(libs.chuclerNoOp)

    testImplementation(libs.junit)
    testImplementation(libs.androidx.core)
    testImplementation(libs.mockito)
    testImplementation(libs.mockitoKotlin )
    testImplementation(libs.mockk)
    testImplementation (libs.androidxArchCoreTesting)
    testImplementation(libs.hamcrest)
    testImplementation(libs.androidx.espresso.core)
    testImplementation(libs.hamcrestAll)
    testImplementation(libs.hamacrestLibrary)
    testImplementation(libs.espressoIntents)
    testImplementation(libs.espressoContrib)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation (libs.androidxArchCoreTesting)
    androidTestImplementation(libs.espressoContrib)
    androidTestImplementation(libs.espressoIntents)
    androidTestImplementation(libs.hamacrestLibrary)
    androidTestImplementation(libs.androidx.espresso.core)
}
