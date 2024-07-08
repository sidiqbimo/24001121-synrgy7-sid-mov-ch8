package com.bimobelajar.mymovie.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bimobelajar.mymovie.R
import com.bumptech.glide.Glide


class DetailFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var moviePoster: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var movieReleaseDate: TextView
    private lateinit var movieOverview: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        detailViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
            DetailViewModel::class.java)

        moviePoster = view.findViewById(R.id.moviePoster)
        movieTitle = view.findViewById(R.id.movieTitle)
        movieReleaseDate = view.findViewById(R.id.movieReleaseDate)
        movieOverview = view.findViewById(R.id.movieOverview)

        val movieId = arguments?.getInt("movieId") ?: 0
        detailViewModel.fetchMovieDetails(movieId)

        detailViewModel.movie.observe(viewLifecycleOwner, { movie ->
            movieTitle.text = movie.title
            movieReleaseDate.text = movie.release_date
            movieOverview.text = movie.overview
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .into(moviePoster)
        })

        return view
    }
}
