package com.sequenia.kmp.di

import org.koin.dsl.koinConfiguration

fun appKoinConfiguration() = koinConfiguration {
    modules(
        networkModule,
        dataSourcesModule,
        repositoriesModule,
        modelsModule,
        viewModelsModule,
    )
}