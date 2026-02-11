package com.sequenia.kmp.data.data_store_preferences.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun createSettingsDataStore(context: Context): DataStore<Preferences> {
    return createSettingsDataStore(
        producePath = {
            context.filesDir.resolve(settingsDataStorePreferencesFileName).absolutePath
        }
    )
}