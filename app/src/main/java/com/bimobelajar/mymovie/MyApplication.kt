package com.bimobelajar.mymovie

import android.annotation.SuppressLint
import android.app.Application
import com.bimobelajar.core.data.DataStoreManager
import com.bimobelajar.mymovie.di.NetworkModule
import retrofit2.Retrofit

class MyApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var dataStoreManager: DataStoreManager
            private set

        lateinit var retrofit: Retrofit
            private set
    }

    override fun onCreate() {
        super.onCreate()
        dataStoreManager = DataStoreManager.getInstance(applicationContext)
        retrofit = NetworkModule.provideRetrofit(this)
    }
}
