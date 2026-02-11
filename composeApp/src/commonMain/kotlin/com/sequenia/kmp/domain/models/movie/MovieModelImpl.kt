package com.sequenia.kmp.domain.models.movie

import com.sequenia.kmp.domain.entities.movie.Movie
import com.sequenia.kmp.domain.repositories.movies.MoviesRepository
import com.sequenia.kmp.domain.results.ExecutionResult

class MovieModelImpl(
    private val moviesRepository: MoviesRepository
) : MovieModel {

    override suspend fun loadMovie(movieId: Long): ExecutionResult<Movie?> {
        return moviesRepository.loadLocalMovie(movieId)
    }
}