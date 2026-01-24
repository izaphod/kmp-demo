package com.sequenia.kmp.domain.models.movies

import com.sequenia.kmp.domain.entities.movie.Movie
import ru.sequenia.test.domain.entities.results.ExecutionResult

interface MoviesModel {

    suspend fun loadMovies(): ExecutionResult<List<Movie>>
}