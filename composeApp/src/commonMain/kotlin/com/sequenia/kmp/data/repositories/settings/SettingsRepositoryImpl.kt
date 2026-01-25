package com.sequenia.kmp.data.repositories.settings

import com.sequenia.kmp.data.data_store_preferences.settings.SettingsDataStorePreferences
import com.sequenia.kmp.domain.entities.settings.GenresSelectionMode
import com.sequenia.kmp.domain.repositories.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val settingsDataStorePreferences: SettingsDataStorePreferences
) : SettingsRepository {

    override fun subscribeToGenresSelectionMode(): Flow<GenresSelectionMode?> {
        return settingsDataStorePreferences.subscribeToGenresSelectionMode()
    }

    override suspend fun getGenresSelectionMode(): GenresSelectionMode? {
        return settingsDataStorePreferences.getGenresSelectionMode()
    }

    override suspend fun saveGenresSelectionMode(mode: GenresSelectionMode) {
        settingsDataStorePreferences.saveGenresSelectionMode(mode)
    }
}