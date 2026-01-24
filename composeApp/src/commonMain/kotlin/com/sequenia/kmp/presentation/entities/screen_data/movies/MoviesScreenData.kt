package com.sequenia.kmp.presentation.entities.screen_data.movies

import com.sequenia.kmp.domain.entities.movie.Movie
import com.sequenia.kmp.domain.entities.settings.GenresSelectionMode

data class MoviesScreenData(
    val movies: List<Movie> = emptyList(),
    val genres: List<String> = emptyList(),
    val selectedGenres: Set<String> = emptySet(),
    val genreSelectionMode: GenresSelectionMode = GenresSelectionMode.MULTIPLE,
)