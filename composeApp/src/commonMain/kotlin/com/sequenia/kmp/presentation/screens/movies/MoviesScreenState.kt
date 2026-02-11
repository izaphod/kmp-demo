package com.sequenia.kmp.presentation.screens.movies

import com.sequenia.kmp.domain.results.ExecutionResult
import com.sequenia.kmp.presentation.entities.screen_data.movies.MoviesScreenData

sealed interface MoviesScreenState {

    data object InitialState : MoviesScreenState

    data object LoadingState : MoviesScreenState

    data class SuccessState(
        val data: MoviesScreenData
    ) : MoviesScreenState

    data class ErrorState(
        val errorResult: ExecutionResult.Error
    ) : MoviesScreenState
}