package com.sequenia.kmp.data.data_sources.movies

import com.sequenia.kmp.data.entities.response.MoviesResponse

interface MoviesDataSourceRemote {

    suspend fun loadMovies(): MoviesResponse
}