package com.sequenia.kmp.presentation.navigation.main

import androidx.compose.foundation.background
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
import com.sequenia.kmp.presentation.navigation.navigator.CommonNavigator
import com.sequenia.kmp.presentation.navigation.bottom.BottomNavigationRoot
import com.sequenia.kmp.presentation.navigation.routes.BOTTOM_NAV_TOP_LEVEL_DESTINATIONS
import com.sequenia.kmp.presentation.navigation.routes.Route
import com.sequenia.kmp.presentation.navigation.state.rememberMultiStackNavigationState
import com.sequenia.kmp.presentation.navigation.state.rememberSimpleNavigationState
import com.sequenia.kmp.presentation.navigation.state.toEntries
import com.sequenia.kmp.presentation.screens.favorites.FavoritesScreen
import com.sequenia.kmp.presentation.screens.settings.SettingsScreen
import com.sequenia.kmp.presentation.screens.webview.WebViewScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.viewmodel.koinViewModel

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
            subclass(
                subclass = Route.WebViewRoute::class,
                serializer = Route.WebViewRoute.serializer()
            )
        }
    }
}

@Composable
fun MainNavigationRoot(modifier: Modifier = Modifier) {
    val surface = AppTheme.colorSystem.surface

    val mainNavState = rememberSimpleNavigationState(
        startKey = Route.BottomNavigationRoute,
        configuration = savedStateConfiguration
    )
    @Suppress("USELESS_CAST")
    val bottomNavState = rememberMultiStackNavigationState(
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
                        FavoritesScreen(
                            navigator = mainNavigator,
                            modifier = Modifier.background(color = surface)
                        )
                    }
                    entry<Route.SettingsRoute> {
                        SettingsScreen(
                            viewModel = koinViewModel(),
                            navigator = mainNavigator,
                            isFullScreen = true,
                            modifier = Modifier.background(color = surface)
                        )
                    }
                    entry<Route.WebViewRoute> {
                        WebViewScreen(
                            navigator = mainNavigator,
                            modifier = Modifier.background(color = surface)
                        )
                    }
                }
            ),
            onBack = mainNavigator::goBack,
            modifier = Modifier.fillMaxSize()
        )
    }
}