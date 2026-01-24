package com.sequenia.kmp.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.sequenia.kmp.presentation.compose.theme.AppTheme
import com.sequenia.kmp.presentation.navigation.bottom.BottomNavigationRoot
import com.sequenia.kmp.presentation.navigation.routes.BOTTOM_NAV_TOP_LEVEL_DESTINATIONS
import com.sequenia.kmp.presentation.navigation.routes.Route
import com.sequenia.kmp.presentation.screens.favorites.FavoritesScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

val savedStateConfiguration = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(
                subclass = Route.BottomNavigationRoute::class,
                serializer = Route.BottomNavigationRoute.serializer()
            )
            subclass(
                subclass = Route.MoviesRoute::class,
                serializer = Route.MoviesRoute.serializer()
            )
            subclass(
                subclass = Route.SettingsRoute::class,
                serializer = Route.SettingsRoute.serializer()
            )
            subclass(
                subclass = Route.FavoritesRoute::class,
                serializer = Route.FavoritesRoute.serializer()
            )
            subclass(
                subclass = Route.MovieDetailsRoute::class,
                serializer = Route.MovieDetailsRoute.serializer()
            )
        }
    }
}

@Composable
fun MainNavigationRoot(modifier: Modifier = Modifier) {
    val surface = AppTheme.colorSystem.surface

    val mainNavState = rememberNavigationState(
        startKey = Route.BottomNavigationRoute,
        topLevelKeys = setOf(Route.BottomNavigationRoute),
        configuration = savedStateConfiguration
    )
    @Suppress("USELESS_CAST")
    val bottomNavState = rememberNavigationState(
        startKey = Route.MoviesRoute,
        topLevelKeys = BOTTOM_NAV_TOP_LEVEL_DESTINATIONS.keys as Set<Route>,
        configuration = savedStateConfiguration
    )
    val commonNavigator = remember {
        CommonNavigator(
            mainNavState = mainNavState,
            bottomNavState = bottomNavState
        )
    }
    val mainNavigator = commonNavigator.mainNavigator

    Surface(
        color = surface,
        modifier = modifier
    ) {
        NavDisplay(
            entries = mainNavState.toEntries(
                entryProvider = entryProvider {
                    entry<Route.BottomNavigationRoute> {
                        BottomNavigationRoot(
                            navigationState = bottomNavState,
                            commonNavigator = commonNavigator,
                        )
                    }
                    entry<Route.FavoritesRoute> {
                        FavoritesScreen(navigator = mainNavigator)
                    }
                }
            ),
            onBack = mainNavigator::goBack,
            modifier = Modifier.fillMaxSize()
        )
    }
}