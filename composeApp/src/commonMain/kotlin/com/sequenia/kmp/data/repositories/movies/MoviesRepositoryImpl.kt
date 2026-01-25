package com.sequenia.kmp.data.repositories.movies

import com.sequenia.kmp.data.data_sources.movies.MoviesDataSourceLocal
import com.sequenia.kmp.data.data_sources.movies.MoviesDataSourceRemote
import com.sequenia.kmp.data.network.ApiRequestExecutor
import com.sequenia.kmp.domain.entities.movie.Movie
import com.sequenia.kmp.domain.repositories.movies.MoviesRepository
import com.sequenia.kmp.domain.results.ExecutionResult

class MoviesRepositoryImpl(
    private val apiRequestExecutor: ApiRequestExecutor,
    private val moviesDataSourceRemote: MoviesDataSourceRemote,
    private val moviesDataSourceLocal: MoviesDataSourceLocal
) : MoviesRepository {

    override suspend fun loadMovies(): ExecutionResult<List<Movie>> {
        return apiRequestExecutor.execute {
            moviesDataSourceRemote.loadMovies().movies
        }
    }

    override suspend fun saveLocalMovies(movies: List<Movie>) {
        moviesDataSourceLocal.saveMovies(movies)
    }

    override suspend fun loadLocalMovie(movieId: Long): ExecutionResult<Movie?> {
        val movies = moviesDataSourceLocal.loadMovies()
        val movie = movies.firstOrNull { movie -> movie.id == movieId }

        return ExecutionResult.Success(movie)
    }
}