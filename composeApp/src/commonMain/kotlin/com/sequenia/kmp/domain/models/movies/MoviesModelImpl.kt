package com.sequenia.kmp.domain.models.movies

import com.sequenia.kmp.domain.entities.movie.Movie
import com.sequenia.kmp.domain.repositories.movies.MoviesRepository
import com.sequenia.kmp.domain.results.ExecutionResult

class MoviesModelImpl(
    private val moviesRepository: MoviesRepository
) : MoviesModel {

    override suspend fun loadMovies(): ExecutionResult<List<Movie>> {
        return moviesRepository.loadMovies()
    }
}