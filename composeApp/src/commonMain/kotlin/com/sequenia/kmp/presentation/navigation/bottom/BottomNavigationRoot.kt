@file:OptIn(ExperimentalMaterial3Api::class)

package com.sequenia.kmp.presentation.navigation.bottom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.sequenia.kmp.presentation.compose.theme.AppTheme
import com.sequenia.kmp.presentation.navigation.navigator.CommonNavigator
import com.sequenia.kmp.presentation.navigation.state.MultiStackNavigationState
import com.sequenia.kmp.presentation.navigation.routes.BOTTOM_NAV_TOP_LEVEL_DESTINATIONS
import com.sequenia.kmp.presentation.navigation.routes.Route
import com.sequenia.kmp.presentation.navigation.state.toEntries
import com.sequenia.kmp.presentation.screens.movie_details.MovieDetailsScreen
import com.sequenia.kmp.presentation.screens.movies.MoviesScreen
import com.sequenia.kmp.presentation.screens.settings.SettingsScreen
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun BottomNavigationRoot(
    navigationState: MultiStackNavigationState,
    commonNavigator: CommonNavigator,
    modifier: Modifier = Modifier,
) {
    val surface = AppTheme.colorSystem.surface
    val bottomNavNavigator = commonNavigator.bottomNavNavigator

    Column(
        modifier = modifier.background(color = surface)
    ) {
        NavDisplay(
            entries = navigationState.toEntries(
                entryProvider = entryProvider {
                    entry<Route.MoviesRoute> {
                        MoviesScreen(
                            viewModel = koinViewModel(),
                            commonNavigator = commonNavigator,
                            modifier = Modifier.background(color = surface)
                        )
                    }
                    entry<Route.MovieDetailsRoute> { route ->
                        MovieDetailsScreen(
                            viewModel = koinViewModel { parametersOf(route.movieId) },
                            navigator = bottomNavNavigator,
                            modifier = Modifier.background(color = surface)
                        )
                    }
                    entry<Route.SettingsRoute> {
                        SettingsScreen(
                            viewModel = koinViewModel(),
                            navigator = bottomNavNavigator,
                            modifier = Modifier.background(color = surface)
                        )
                    }
                }
            ),
            onBack = bottomNavNavigator::goBack,
            modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 1F),
        )

        BottomNavigationBarComponent(
            selectedKey = navigationState.currentTopLevelKey as Route,
            onSelectKey = { topLevelKey -> bottomNavNavigator.navigate(topLevelKey) }
        )
    }
}

@Composable
private fun BottomNavigationBarComponent(
    selectedKey: Route,
    onSelectKey: (Route) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier,
        containerColor = AppTheme.colorSystem.appBarContainer,
    ) {
        BOTTOM_NAV_TOP_LEVEL_DESTINATIONS.forEach { (topLevelDestination, navItem) ->
            val label = stringResource(navItem.labelResource)

            NavigationBarItem(
                selected = topLevelDestination == selectedKey,
                onClick = { onSelectKey(topLevelDestination) },
                icon = {
                    Icon(
                        imageVector = vectorResource(navItem.iconResource),
                        contentDescription = label,
                    )
                },
                label = {
                    Text(
                        text = label,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppTheme.colorSystem.selectedGenreContainer,
                    selectedTextColor = AppTheme.colorSystem.selectedGenreContainer,
                    unselectedIconColor = AppTheme.colorSystem.appBarContent,
                    unselectedTextColor = AppTheme.colorSystem.appBarContent,
                    indicatorColor = Color.Transparent,
                )
            )
        }
    }
}