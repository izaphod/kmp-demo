package com.sequenia.kmp.presentation.compose_preview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.sequenia.kmp.di.viewModelsModule
import com.sequenia.kmp.domain.entities.movie.Movie
import com.sequenia.kmp.domain.entities.settings.GenresSelectionMode
import com.sequenia.kmp.domain.models.movie.MovieModel
import com.sequenia.kmp.domain.models.movies.MoviesModel
import com.sequenia.kmp.domain.models.settings.SettingsModel
import com.sequenia.kmp.domain.results.ExecutionResult
import com.sequenia.kmp.presentation.compose.theme.AppTheme
import com.sequenia.kmp.presentation.navigation.navigator.CommonNavigator
import com.sequenia.kmp.presentation.navigation.navigator.Navigator
import com.sequenia.kmp.presentation.navigation.state.MultiStackNavigationState
import com.sequenia.kmp.presentation.navigation.state.SingleStackNavigationState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.koin.compose.KoinApplication
import org.koin.dsl.koinConfiguration
import org.koin.dsl.module

@Composable
internal fun PreviewApp(content: @Composable () -> Unit) {
    KoinApplication(
        configuration = koinConfiguration {
            modules(previewModelsModule, viewModelsModule, navigatorsModule)
        }
    ) {
        AppTheme {
            Surface(
                color = AppTheme.colorSystem.surface,
                content = content,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

private val previewModelsModule = module {
    single<List<Movie>> {
        listOf(
            Movie(
                id = 123L,
                name = "Inception",
                localizedName = "Начало",
                year = 2010,
                rating = 8.8f,
                posterUrl = "https://example.com/poster.jpg",
                description = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
                genres = listOf("Sci-Fi", "Action", "Thriller")
            ),
            Movie(
                id = 456L,
                name = "Interstellar",
                localizedName = "Интерстеллар",
                year = 2014,
                rating = 8.6f,
                posterUrl = "https://example.com/interstellar.jpg",
                description = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
                genres = listOf("Sci-Fi", "Drama")
            )
        )
    }

    factory<MoviesModel> {
        object : MoviesModel {
            override suspend fun loadMovies(): ExecutionResult<List<Movie>> {
                return ExecutionResult.Success(get())
            }

            override suspend fun saveMovies(movies: List<Movie>) {}
        }
    }

    factory<MovieModel> {
        object : MovieModel {
            override suspend fun loadMovie(movieId: Long): ExecutionResult<Movie?> {
                val movies: List<Movie> = get()

                return ExecutionResult.Success(movies.first())
            }
        }
    }

    factory<SettingsModel> {
        object : SettingsModel {
            override fun subscribeToGenresSelectionMode(): Flow<GenresSelectionMode> {
                return flowOf(GenresSelectionMode.MULTIPLE)
            }

            override suspend fun getGenresSelectionMode(): GenresSelectionMode {
                return GenresSelectionMode.MULTIPLE
            }

            override suspend fun saveGenresSelectionMode(mode: GenresSelectionMode) {}
        }
    }
}

private val navigatorsModule = module {
    single<Navigator> {
        object : Navigator {
            override fun navigate(key: NavKey) {}

            override fun navigate(
                key: NavKey,
                popUpTo: NavKey,
                isInclusive: Boolean,
                findLast: Boolean
            ) {}

            override fun goBack() {}
        }
    }

    single {
        val mainNavState = SingleStackNavigationState(
            startKey = object : NavKey {},
            backStack = NavBackStack()
        )
        val bottomNavState = MultiStackNavigationState(
            startKey = object : NavKey {},
            topLevelStack = NavBackStack(),
            subStacks = mapOf()
        )

        CommonNavigator(
            mainNavState = mainNavState,
            bottomNavState = bottomNavState
        )
    }
}