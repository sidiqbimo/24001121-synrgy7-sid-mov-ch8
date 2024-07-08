package com.bimobelajar.mymovie.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bimobelajar.core.data.model.Movie
import com.bimobelajar.core.data.network.RetrofitService
import com.bimobelajar.core.data.network.TMDBApi
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val apiService: TMDBApi = RetrofitService.getInstance(application)

    init {
        fetchNowPlayingMovies()
    }

    private fun fetchNowPlayingMovies() {
        viewModelScope.launch {
            val response = apiService.getNowPlayingMovies("3a7662166bf5d5c5ab802f419b292871")
            if (response.isSuccessful) {
                _movies.postValue(response.body()?.results)
            }
        }
    }
}
