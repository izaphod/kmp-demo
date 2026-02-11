package com.sequenia.kmp.domain.models.settings

import com.sequenia.kmp.domain.entities.settings.GenresSelectionMode
import kotlinx.coroutines.flow.Flow

interface SettingsModel {

    fun subscribeToGenresSelectionMode(): Flow<GenresSelectionMode>

    suspend fun getGenresSelectionMode(): GenresSelectionMode

    suspend fun saveGenresSelectionMode(mode: GenresSelectionMode)
}