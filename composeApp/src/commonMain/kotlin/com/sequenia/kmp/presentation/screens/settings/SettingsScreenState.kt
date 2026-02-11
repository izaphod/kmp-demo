package com.sequenia.kmp.presentation.screens.settings

import com.sequenia.kmp.domain.entities.settings.GenresSelectionMode

sealed interface SettingsScreenState {

    data object InitialState : SettingsScreenState

    data class SuccessState(
        val sortSelectionMode: GenresSelectionMode
    ) : SettingsScreenState
}