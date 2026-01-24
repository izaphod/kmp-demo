package com.sequenia.kmp.domain.models.movies

import com.sequenia.kmp.domain.entities.movie.Movie
import com.sequenia.kmp.domain.repositories.movies.MoviesRepository
import ru.sequenia.test.domain.entities.results.ExecutionResult

class MoviesModelImpl(
    private val moviesRepository: MoviesRepository
) : MoviesModel {

    override suspend fun loadMovies(): ExecutionResult<List<Movie>> {
        return moviesRepository.loadMovies()
    }
}