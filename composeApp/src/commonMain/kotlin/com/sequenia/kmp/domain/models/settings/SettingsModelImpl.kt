package com.sequenia.kmp.domain.models.settings

import com.sequenia.kmp.domain.entities.settings.GenresSelectionMode
import com.sequenia.kmp.domain.repositories.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsModelImpl(
    private val settingsRepository: SettingsRepository
) : SettingsModel {

    private val defaultGenresSelectionMode = GenresSelectionMode.MULTIPLE

    override fun subscribeToGenresSelectionMode(): Flow<GenresSelectionMode> {
        return settingsRepository.subscribeToGenresSelectionMode().map { mode ->
            mode ?: defaultGenresSelectionMode
        }
    }

    override suspend fun getGenresSelectionMode(): GenresSelectionMode {
        return settingsRepository.getGenresSelectionMode() ?: defaultGenresSelectionMode
    }

    override suspend fun saveGenresSelectionMode(mode: GenresSelectionMode) {
        settingsRepository.saveGenresSelectionMode(mode)
    }
}