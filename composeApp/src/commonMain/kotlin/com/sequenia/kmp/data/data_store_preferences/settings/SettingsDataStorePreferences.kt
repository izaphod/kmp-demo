package com.sequenia.kmp.data.data_store_preferences.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sequenia.kmp.domain.entities.settings.GenresSelectionMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SettingsDataStorePreferences(
    private val settingsDataStore: DataStore<Preferences>
) {

    private object PreferencesKeys {
        val GENRES_SELECTION_MODE = stringPreferencesKey("genres_selection_mode")
    }

    fun subscribeToGenresSelectionMode(): Flow<GenresSelectionMode?> {
        return settingsDataStore.data.map { mapPreferencesToGenresSelectionMode(it) }
    }

    suspend fun getGenresSelectionMode(): GenresSelectionMode? {
        return settingsDataStore.data.map { mapPreferencesToGenresSelectionMode(it) }.first()
    }

    suspend fun saveGenresSelectionMode(mode: GenresSelectionMode) {
        settingsDataStore.edit { preferences ->
            preferences[PreferencesKeys.GENRES_SELECTION_MODE] = mode.name
        }
    }

    private fun mapPreferencesToGenresSelectionMode(
        preferences: Preferences
    ): GenresSelectionMode? {
        val modeName = preferences[PreferencesKeys.GENRES_SELECTION_MODE]

        return modeName?.let { GenresSelectionMode.valueOf(it) }
    }
}