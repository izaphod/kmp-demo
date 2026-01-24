package com.sequenia.kmp.data.repositories.settings

import com.sequenia.kmp.domain.entities.settings.GenresSelectionMode
import com.sequenia.kmp.domain.repositories.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsRepositoryImpl() : SettingsRepository {

    private val selectionModeStateFlow = MutableStateFlow(GenresSelectionMode.MULTIPLE)

    override fun subscribeToGenresSelectionMode(): Flow<GenresSelectionMode?> {
        return selectionModeStateFlow.asStateFlow()
    }

    override suspend fun getGenresSelectionMode(): GenresSelectionMode? {
        return selectionModeStateFlow.value
    }

    override suspend fun saveGenresSelectionMode(mode: GenresSelectionMode) {
        selectionModeStateFlow.value = mode
    }
}