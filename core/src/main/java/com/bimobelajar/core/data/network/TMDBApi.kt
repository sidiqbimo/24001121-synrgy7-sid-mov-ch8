package com.bimobelajar.core.data.network

import com.bimobelajar.core.data.model.Movie
import com.bimobelajar.core.data.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = "3a7662166bf5d5c5ab802f419b292871",
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = "3a7662166bf5d5c5ab802f419b292871",
        @Query("language") language: String = "en-US"
    ): Response<Movie>
}