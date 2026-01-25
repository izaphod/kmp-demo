package com.sequenia.kmp.presentation.navigation.routes

import androidx.navigation3.runtime.NavKey
import com.sequenia.kmp.presentation.navigation.bottom.BottomNavItem
import kotlinx.serialization.Serializable
import sequeniakmp.composeapp.generated.resources.Res
import sequeniakmp.composeapp.generated.resources.ic_outline_movie_24
import sequeniakmp.composeapp.generated.resources.ic_outline_settings_24
import sequeniakmp.composeapp.generated.resources.title_movies
import sequeniakmp.composeapp.generated.resources.title_settings

@Serializable
sealed interface Route : NavKey {

    @Serializable
    data object BottomNavigationRoute : Route

    @Serializable
    data object FavoritesRoute : Route

    @Serializable
    data object MoviesRoute : Route

    @Serializable
    data class MovieDetailsRoute(val movieId: Long) : Route

    @Serializable
    data object SettingsRoute : Route
}

val BOTTOM_NAV_TOP_LEVEL_DESTINATIONS: Map<Route, BottomNavItem> = mapOf(
    Route.MoviesRoute to BottomNavItem(
        iconResource = Res.drawable.ic_outline_movie_24,
        labelResource = Res.string.title_movies
    ),
    Route.SettingsRoute to BottomNavItem(
        iconResource = Res.drawable.ic_outline_settings_24,
        labelResource = Res.string.title_settings
    ),
)