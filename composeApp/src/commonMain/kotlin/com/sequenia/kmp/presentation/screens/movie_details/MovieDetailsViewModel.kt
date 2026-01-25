package com.sequenia.kmp.presentation.screens.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sequenia.kmp.domain.entities.movie.Movie
import com.sequenia.kmp.domain.models.movie.MovieModel
import com.sequenia.kmp.domain.results.ExecutionResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    movieId: Long,
    private val movieModel: MovieModel
) : ViewModel() {

    val movieStateFlow get() = movieMutableState.asStateFlow()

    private val movieMutableState = MutableStateFlow<Movie?>(null)

    init {
        viewModelScope.launch {
            val movie = (movieModel.loadMovie(movieId) as? ExecutionResult.Success)?.data
            movieMutableState.value = movie
        }
    }
}