package com.sequenia.kmp.data.repositories.movies

import com.sequenia.kmp.data.data_sources.movies.MoviesDataSource
import com.sequenia.kmp.data.network.ApiRequestExecutor
import com.sequenia.kmp.domain.entities.movie.Movie
import com.sequenia.kmp.domain.repositories.movies.MoviesRepository
import com.sequenia.kmp.domain.results.ExecutionResult

class MoviesRepositoryImpl(
    private val apiRequestExecutor: ApiRequestExecutor,
    private val moviesDataSource: MoviesDataSource
) : MoviesRepository {

    override suspend fun loadMovies(): ExecutionResult<List<Movie>> {
        return apiRequestExecutor.execute {
            moviesDataSource.loadMovies().movies
        }
    }
}