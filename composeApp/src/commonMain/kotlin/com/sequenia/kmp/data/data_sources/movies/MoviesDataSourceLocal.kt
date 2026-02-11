package com.sequenia.kmp.data.data_sources.movies

import com.sequenia.kmp.domain.entities.movie.Movie

interface MoviesDataSourceLocal {

    suspend fun loadMovies(): List<Movie>

    suspend fun saveMovies(movies: List<Movie>)
}