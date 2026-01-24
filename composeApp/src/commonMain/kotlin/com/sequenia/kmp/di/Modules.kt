package com.sequenia.kmp.di

import com.sequenia.kmp.data.data_sources.movies.MoviesDataSource
import com.sequenia.kmp.data.data_sources.movies.MoviesDataSourceRemoteImpl
import com.sequenia.kmp.data.network.ApiRequestExecutor
import com.sequenia.kmp.data.network.NetworkRequestExecutor
import com.sequenia.kmp.data.repositories.movies.MoviesRepositoryImpl
import com.sequenia.kmp.data.repositories.settings.SettingsRepositoryImpl
import com.sequenia.kmp.domain.models.movies.MoviesModel
import com.sequenia.kmp.domain.models.movies.MoviesModelImpl
import com.sequenia.kmp.domain.models.settings.SettingsModel
import com.sequenia.kmp.domain.models.settings.SettingsModelImpl
import com.sequenia.kmp.domain.repositories.movies.MoviesRepository
import com.sequenia.kmp.domain.repositories.settings.SettingsRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(get())
            }

            install(HttpTimeout) {
                connectTimeoutMillis = 60000L
                requestTimeoutMillis = 60000L
            }

            defaultRequest {
                url("https://s3-eu-west-1.amazonaws.com/sequeniatesttask/")
                contentType(ContentType.Application.Json)
            }
        }
    }

    single {
        Json {
            coerceInputValues = true
            ignoreUnknownKeys = true
            explicitNulls = false
        }
    }

    factory {
        NetworkRequestExecutor()
    }

    factory {
        ApiRequestExecutor(
            networkRequestExecutor = get(),
            json = get()
        )
    }
}

val dataSourcesModule = module {
    factory<MoviesDataSource> {
        MoviesDataSourceRemoteImpl(httpClient = get())
    }
}

val repositoriesModule = module {
    factory<MoviesRepository> {
        MoviesRepositoryImpl(
            apiRequestExecutor = get(),
            moviesDataSource = get()
        )
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl()
    }
}

val modelsModule = module {
    factory<MoviesModel> {
        MoviesModelImpl(
            moviesRepository = get()
        )
    }

    factory<SettingsModel> {
        SettingsModelImpl(
            settingsRepository = get()
        )
    }
}