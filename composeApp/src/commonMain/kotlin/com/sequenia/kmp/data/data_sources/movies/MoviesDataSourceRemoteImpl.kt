package com.sequenia.kmp.data.data_sources.movies

import com.sequenia.kmp.data.entities.response.MoviesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MoviesDataSourceRemoteImpl(private val httpClient: HttpClient) : MoviesDataSource {

    override suspend fun loadMovies(): MoviesResponse {
        return httpClient.get("films.json").body()
    }
}