package com.sequenia.kmp.di

import com.sequenia.kmp.data.data_sources.movies.MoviesDataSourceLocal
import com.sequenia.kmp.data.data_sources.movies.MoviesDataSourceLocalImpl
import com.sequenia.kmp.data.data_sources.movies.MoviesDataSourceRemote
import com.sequenia.kmp.data.data_sources.movies.MoviesDataSourceRemoteImpl
import com.sequenia.kmp.data.database.MoviesDatabase
import com.sequenia.kmp.data.database.getRoomDatabase
import com.sequenia.kmp.data.network.ApiRequestExecutor
import com.sequenia.kmp.data.network.NetworkRequestExecutor
import com.sequenia.kmp.data.repositories.movies.MoviesRepositoryImpl
import com.sequenia.kmp.data.repositories.settings.SettingsRepositoryImpl
import com.sequenia.kmp.domain.models.movie.MovieModel
import com.sequenia.kmp.domain.models.movie.MovieModelImpl
import com.sequenia.kmp.domain.models.movies.MoviesModel
import com.sequenia.kmp.domain.models.movies.MoviesModelImpl
import com.sequenia.kmp.domain.models.settings.SettingsModel
import com.sequenia.kmp.domain.models.settings.SettingsModelImpl
import com.sequenia.kmp.domain.repositories.movies.MoviesRepository
import com.sequenia.kmp.domain.repositories.settings.SettingsRepository
import com.sequenia.kmp.presentation.screens.movie_details.MovieDetailsViewModel
import com.sequenia.kmp.presentation.screens.movies.MoviesViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.sequenia.test.ui.screens.settings.SettingsViewModel

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

val databasesModule = module {
    single<MoviesDatabase> {
        getRoomDatabase(builder = get())
    }

    single {
        get<MoviesDatabase>().moviesDao()
    }
}

val dataSourcesModule = module {
    factory<MoviesDataSourceRemote> {
        MoviesDataSourceRemoteImpl(httpClient = get())
    }

    factory<MoviesDataSourceLocal> {
        MoviesDataSourceLocalImpl(moviesDao = get())
    }
}

val repositoriesModule = module {
    factory<MoviesRepository> {
        MoviesRepositoryImpl(
            apiRequestExecutor = get(),
            moviesDataSourceRemote = get(),
            moviesDataSourceLocal = get()
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

    factory<MovieModel> {
        MovieModelImpl(
            moviesRepository = get()
        )
    }

    factory<SettingsModel> {
        SettingsModelImpl(
            settingsRepository = get()
        )
    }
}

val viewModelsModule = module {
    viewModel {
        MoviesViewModel(
            moviesModel = get(),
            settingsModel = get(),
        )
    }

    viewModel { (movieId: Long) ->
        MovieDetailsViewModel(
            movieId = movieId,
            movieModel = get()
        )
    }

    viewModel {
        SettingsViewModel(
            settingsModel = get()
        )
    }
}