package com.sequenia.kmp.domain.repositories.movies

import com.sequenia.kmp.domain.entities.movie.Movie
import com.sequenia.kmp.domain.results.ExecutionResult

interface MoviesRepository {

    suspend fun loadMovies(): ExecutionResult<List<Movie>>

    suspend fun saveLocalMovies(movies: List<Movie>)

    suspend fun loadLocalMovie(movieId: Long): ExecutionResult<Movie?>
}