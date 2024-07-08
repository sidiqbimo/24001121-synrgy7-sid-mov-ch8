package com.bimobelajar.core.data.network

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.chuckerteam.chucker.api.ChuckerInterceptor

class RetrofitService {

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private var retrofitService: TMDBApi? = null

        fun getInstance(context: Context): TMDBApi {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(provideOkHttpClient(context))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(TMDBApi::class.java)
            }
            return retrofitService!!
        }

        private fun provideOkHttpClient(context: Context): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(ChuckerInterceptor.Builder(context).build())
                .build()
        }
    }
}
