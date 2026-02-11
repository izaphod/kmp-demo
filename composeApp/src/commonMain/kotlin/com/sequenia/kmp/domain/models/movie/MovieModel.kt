package com.sequenia.kmp.domain.models.movie

import com.sequenia.kmp.domain.entities.movie.Movie
import com.sequenia.kmp.domain.results.ExecutionResult

interface MovieModel {

    suspend fun loadMovie(movieId: Long): ExecutionResult<Movie?>
}