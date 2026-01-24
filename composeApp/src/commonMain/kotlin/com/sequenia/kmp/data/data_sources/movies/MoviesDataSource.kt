package com.sequenia.kmp.data.data_sources.movies

import com.sequenia.kmp.data.entities.response.MoviesResponse

interface MoviesDataSource {

    suspend fun loadMovies(): MoviesResponse
}