package com.sequenia.kmp.presentation.screens.movies

sealed interface MoviesScreenEvent {

    data class ShowMovieEvent(val movieId: Long) : MoviesScreenEvent

    data object ShowFavoritesEvent : MoviesScreenEvent
}