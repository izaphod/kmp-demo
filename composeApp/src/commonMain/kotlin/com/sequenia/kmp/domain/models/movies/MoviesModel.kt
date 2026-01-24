package com.sequenia.kmp.domain.models.movies

import com.sequenia.kmp.domain.entities.movie.Movie
import com.sequenia.kmp.domain.results.ExecutionResult

interface MoviesModel {

    suspend fun loadMovies(): ExecutionResult<List<Movie>>
}