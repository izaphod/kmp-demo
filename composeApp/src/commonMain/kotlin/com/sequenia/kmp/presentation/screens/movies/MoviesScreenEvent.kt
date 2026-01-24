package com.sequenia.kmp.presentation.screens.movies

import com.sequenia.kmp.domain.entities.movie.Movie

sealed interface MoviesScreenEvent {

    data class ShowMovieEvent(val movie: Movie) : MoviesScreenEvent

    data object ShowFavoritesEvent : MoviesScreenEvent
}