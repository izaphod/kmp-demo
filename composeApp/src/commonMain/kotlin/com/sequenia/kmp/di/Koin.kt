package com.sequenia.kmp.di

import org.koin.dsl.koinConfiguration

fun appKoinConfiguration() = koinConfiguration {
    modules(
        platformModule,
        networkModule,
        databasesModule,
        dataStoresModule,
        dataSourcesModule,
        repositoriesModule,
        modelsModule,
        viewModelsModule,
    )
}