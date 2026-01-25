package com.sequenia.kmp.di

import com.sequenia.kmp.data.database.getDatabaseBuilder
import org.koin.dsl.module

actual val platformModule = module {
    single {
        getDatabaseBuilder()
    }
}