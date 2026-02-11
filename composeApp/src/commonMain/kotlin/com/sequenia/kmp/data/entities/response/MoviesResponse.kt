package com.sequenia.kmp.data.entities.response

import com.sequenia.kmp.domain.entities.movie.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    @SerialName("films")
    val movies: List<Movie>
)