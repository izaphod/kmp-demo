package com.sequenia.kmp.di

import com.sequenia.kmp.data.data_store_preferences.settings.createSettingsDataStore
import com.sequenia.kmp.data.database.getDatabaseBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {
    single {
        getDatabaseBuilder(androidContext())
    }

    single {
        createSettingsDataStore(androidContext())
    }
}