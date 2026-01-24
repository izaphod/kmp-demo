package com.sequenia.kmp.domain.repositories.movies

import com.sequenia.kmp.domain.entities.movie.Movie
import ru.sequenia.test.domain.entities.results.ExecutionResult

interface MoviesRepository {

    suspend fun loadMovies(): ExecutionResult<List<Movie>>
}